import {useEffect,useMemo,useState} from "react";
import "./StudentDashboard.css";
import {api} from "../api";
import {LineChart,DonutChart,Notifications,Toast,Modal} from "../components";

const fallbackSubjects=[
    ["BCA301","Operating Systems","Theory"],
    ["BCA302","Computer Networks","Theory"],
    ["BCA303","Software Engineering","Theory"],
    ["BCA304","Python Programming","Theory"],
    ["BCA351","Python Lab","Practical"],
    ["BCA352","Networks Lab","Practical"]
];

export default function StudentDashboard({user,onLogout}){
    const [dark,setDark]=useState(false),[page,setPage]=useState("dashboard");
    const [attendance,setAttendance]=useState([]),[analytics,setAnalytics]=useState(null);
    const [assignments,setAssignments]=useState([]),[sessionals,setSessionals]=useState([]);
    const [profile,setProfile]=useState(null),[editProfile,setEditProfile]=useState(false);
    const [notice,setNotice]=useState(false),[notificationCount,setNotificationCount]=useState(0);
    const [loading,setLoading]=useState(true),[error,setError]=useState(""),[toast,setToast]=useState("");

    const load=async()=>{
        setLoading(true); setError("");
        const results=await Promise.allSettled([
            api.attendance(user.username),api.studentAnalytics(user.username),
            api.assignments(),api.sessionals(user.username),api.student(user.username),api.notifications(user.username)
        ]);
        const [a,an,as,se,pr,no]=results;
        if(a.status==="fulfilled") setAttendance(a.value); else setAttendance([]);
        if(an.status==="fulfilled") setAnalytics(an.value); else setAnalytics(null);
        if(as.status==="fulfilled") setAssignments(as.value); else setAssignments([]);
        if(se.status==="fulfilled") setSessionals(se.value); else setSessionals([]);
        if(pr.status==="fulfilled") setProfile(pr.value); else setProfile(user);
        if(no.status==="fulfilled") setNotificationCount(no.value.filter(x=>!x.read).length); else setNotificationCount(0);
        if(results.some(x=>x.status==="rejected")) setError("Some live data could not be loaded. Check that the Java backend is running.");
        setLoading(false);
    };

    useEffect(()=>{load();},[user.username]);

    const rows=attendance.length?attendance:null;
    const summary=useMemo(()=>{
        if(analytics) return {present:Number(analytics.present)||0,absent:Number(analytics.absent)||0,pct:Number(analytics.attendancePercentage)||0};
        if(rows){const p=rows.filter(x=>x.status==="Present").length,a=rows.filter(x=>x.status==="Absent").length;return {present:p,absent:a,pct:p+a?Math.round(p*1000/(p+a))/10:0};}
        return {present:0,absent:0,pct:0};
    },[analytics,rows]);

    const data=rows?Object.values(rows.reduce((m,x)=>{
        const k=x.subjectCode||"Unknown";
        m[k]??={code:k,name:x.subjectName||"-",total:0,present:0};
        m[k].total++; if(x.status==="Present")m[k].present++; return m;
    },{})).map(x=>({...x,percentage:x.total?Math.round(x.present*100/x.total):0,absent:x.total-x.present,type:"Theory"}))
    :fallbackSubjects.map(x=>({code:x[0],name:x[1],type:x[2],total:0,present:0,absent:0,percentage:0}));

    const nav=[["dashboard","🏠","Dashboard"],["attendance","📅","Attendance"],["assignments","📝","Assignments"],["sessionals","🎯","Sessionals"],["reports","📊","Reports & Analytics"],["notifications","🔔","Notifications"],["profile","👤","My Profile"]];

    return <div className={`portal student-portal ${dark?"dark":""}`}>
        <aside className="portal-sidebar">
            <div className="portal-brand">🎓<div><b>College ERP</b><small>Student Portal</small></div></div>
            <nav>{nav.map(n=><button className={page===n[0]?"active":""} onClick={()=>setPage(n[0])} key={n[0]}><span>{n[1]}</span>{n[2]}</button>)}</nav>
            <div className="sidebar-user"><div className="avatar">{(user.fullName||"ST").slice(0,2).toUpperCase()}</div><div><b>{user.fullName}</b><small>Student</small></div></div>
            <button className="sidebar-logout" onClick={onLogout}>⇥ Logout</button>
        </aside>
        <main className="portal-main">
            <header className="portal-topbar">
                <div><h1>{nav.find(n=>n[0]===page)?.[2]}</h1><small>College ERP / Student Portal</small></div>
                <div className="top-actions">
                    <button onClick={()=>setDark(!dark)}>{dark?"☀️":"🌙"}</button>
                    <button className="bell" onClick={()=>setNotice(true)}>🔔{notificationCount>0&&<i>{notificationCount}</i>}</button>
                    <button className="top-user" onClick={()=>setPage("profile")}><span className="avatar small">{(user.fullName||"ST").slice(0,2).toUpperCase()}</span>{user.fullName} ▾</button>
                </div>
            </header>
            <div className="portal-content">
                {error&&<div className="error-box">{error}<button className="outline" onClick={load}>Retry</button></div>}
                {loading?<div className="card loading-state">Loading live ERP data…</div>:<>
                    {page==="dashboard"&&<Dashboard summary={summary} data={data} setPage={setPage} user={user}/>}
                    {page==="attendance"&&<section className="card"><Head title="Full Attendance Report" sub="Attendance records stored in MySQL" action={<button className="primary" onClick={()=>window.print()}>🖨 Print</button>}/><AttendanceTable data={data}/></section>}
                    {page==="assignments"&&<AssignmentPage assignments={assignments}/>}
                    {page==="sessionals"&&<SessionalPage rows={sessionals}/>}
                    {page==="reports"&&<section className="card"><Head title="Reports & Analytics" sub="Calculated from your real attendance records"/><LineChart data={summary.pct?[{month:"Current",attendance:summary.pct}]:[]}/><div className="report-cards"><Stat label="Present" value={summary.present} tone="green"/><Stat label="Absent" value={summary.absent} tone="red"/><Stat label="Attendance" value={`${summary.pct}%`} tone="blue"/><Stat label="Required" value="75%" tone="purple"/></div></section>}
                    {page==="notifications"&&<section className="card"><Head title="Notifications" sub="Announcements and attendance alerts"/><button className="primary" onClick={()=>setNotice(true)}>🔔 Open Notification Center</button></section>}
                    {page==="profile"&&<ProfilePage profile={profile||user} onEdit={()=>setEditProfile(true)}/>}
                </>}
            </div>
        </main>
        {notice&&<Notifications username={user.username} onClose={()=>{setNotice(false);load();}}/>}
        {editProfile&&<ProfileModal profile={profile||user} onClose={()=>setEditProfile(false)} onSaved={()=>{setEditProfile(false);load();setToast("Profile updated successfully")}}/>}
        {toast&&<Toast message={toast} onClose={()=>setToast("")}/>}
    </div>;
}

function Dashboard({summary,data,setPage,user}){
    return <><section className="welcome"><div><span>WELCOME BACK 👋</span><h2>Hello, {user.fullName}</h2><p>Here is your academic and attendance overview.</p></div><div className="detail-grid"><div><small>Roll No</small><b>{user.username}</b></div><div><small>Course</small><b>{user.course||"BCA"}</b></div><div><small>Semester</small><b>{user.semester||4}</b></div></div></section>
    <section className="stat-grid"><Stat icon="📊" label="Overall Attendance" value={`${summary.pct}%`} tone="blue"/><Stat icon="✓" label="Days Present" value={summary.present} tone="green"/><Stat icon="✕" label="Days Absent" value={summary.absent} tone="red"/><Stat icon="🎓" label="Course" value={user.course||"BCA"} sub={`Semester ${user.semester||4}`} tone="purple"/></section>
    <section className="two-col"><div className="card"><Head title="Attendance Report" sub="Subject-wise attendance details" action={<button className="outline" onClick={()=>setPage("attendance")}>View Full Report</button>}/><AttendanceTable data={data}/></div><div className="card"><Head title="Attendance Distribution" sub="Your current attendance balance"/><DonutChart present={summary.present} absent={summary.absent}/><div className="info-box">Minimum attendance requirement is <b>75%</b>.</div></div></section></>;
}

function AssignmentPage({assignments}){return <section className="card"><Head title="Assignments" sub="Assignments published by faculty"/>{assignments.length?<table><thead><tr><th>Code</th><th>Subject</th><th>Type</th><th>Max Marks</th><th>Details</th></tr></thead><tbody>{assignments.map(a=><tr key={a.id}><td><b>{a.subjectCode}</b></td><td>{a.subjectName}</td><td>{a.type}</td><td>{a.maxMarks??"-"}</td><td>{a.details||"—"}</td></tr>)}</tbody></table>:<div className="empty-state">No assignments published yet.</div>}</section>}

function SessionalPage({rows}){return <section className="card"><Head title="Sessionals" sub="Your S1, S2 and S3 marks"/>{rows.length?<table><thead><tr><th>Subject</th><th>S1 / 20</th><th>S2 / 20</th><th>S3 / 20</th><th>Total / 60</th><th>Status</th></tr></thead><tbody>{rows.map(x=><tr key={x.id}><td><b>{x.subjectCode}</b><br/>{x.subjectName}</td><td>{x.s1??0}</td><td>{x.s2??0}</td><td>{x.s3??0}</td><td><b>{x.total??0}</b></td><td>{x.status}</td></tr>)}</tbody></table>:<div className="empty-state">No sessional marks published yet.</div>}</section>}

function ProfilePage({profile,onEdit}){return <section className="card profile-card"><Head title="My Profile" sub="Student account information" action={<button className="primary" onClick={onEdit}>✏️ Edit Profile</button>}/><div className="profile-large"><div className="avatar xl">{(profile.name||profile.fullName||"ST").slice(0,2).toUpperCase()}</div><div><h2>{profile.name||profile.fullName}</h2><p>{profile.email||"—"}</p></div></div><div className="form-grid"><Field label="Roll No" value={profile.rollNo||profile.username}/><Field label="Course" value={profile.course||"BCA"}/><Field label="Semester" value={profile.semester||4}/><Field label="Section" value={profile.section||"A"}/><Field label="Status" value={profile.status||"Active"}/></div></section>}

function ProfileModal({profile,onClose,onSaved}){const [f,setF]=useState({name:profile.name||profile.fullName||"",email:profile.email||"",course:profile.course||"BCA",semester:profile.semester||4,section:profile.section||"A"});const save=async()=>{try{await api.updateStudentProfile(profile.rollNo||profile.username,f);onSaved();}catch(e){alert(e.message)}};return <Modal title="Edit Profile" onClose={onClose}><div className="form-grid">{[["name","Name"],["email","Email"],["course","Course"],["semester","Semester"],["section","Section"]].map(([k,l])=><label className="field" key={k}><span>{l}</span><input value={f[k]??""} onChange={e=>setF({...f,[k]:e.target.value})}/></label>)}</div><button className="primary" onClick={save} style={{marginTop:18}}>Save Profile</button></Modal>}

function AttendanceTable({data}){return <div className="table-scroll"><table><thead><tr><th>Code</th><th>Subject</th><th>Total</th><th>Present</th><th>Absent</th><th>Attendance</th></tr></thead><tbody>{data.map(x=><tr key={x.code}><td><b>{x.code}</b></td><td>{x.name}</td><td>{x.total}</td><td className="good-text">{x.present}</td><td className="bad-text">{x.absent}</td><td><b>{x.percentage}%</b></td></tr>)}</tbody></table></div>}

function Stat({icon,label,value,sub,tone}){return <div className={`stat ${tone||""}`}><span className="stat-icon">{icon||"◈"}</span><div><small>{label}</small><strong>{value}</strong>{sub&&<em>{sub}</em>}</div></div>}
function Head({title,sub,action}){return <div className="card-head"><div><h3>{title}</h3><p>{sub}</p></div>{action}</div>}
function Field({label,value}){return <label className="field"><span>{label}</span><input value={value??""} readOnly/></label>}
