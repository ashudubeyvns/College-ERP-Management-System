import {useEffect,useState} from "react";
import "./FacultyDashboard.css";
import "../pages/StudentDashboard.css";
import {api} from "../api";
import {LineChart,Modal,Notifications,Toast} from "../components";

const SUBJECT={code:"BCA301",name:"Operating Systems"};

export default function FacultyDashboard({user,onLogout}){
    const [dark,setDark]=useState(false),[page,setPage]=useState("dashboard");
    const [students,setStudents]=useState([]),[attendance,setAttendance]=useState([]);
    const [assignments,setAssignments]=useState([]),[sessionals,setSessionals]=useState([]);
    const [analytics,setAnalytics]=useState(null),[notifications,setNotifications]=useState(false);
    const [modal,setModal]=useState(null),[toast,setToast]=useState(""),[loading,setLoading]=useState(true),[error,setError]=useState("");

    const load=async()=>{
        setLoading(true);setError("");
        const r=await Promise.allSettled([api.students(),api.todayAttendance(SUBJECT.code),api.assignments(),api.sessionals(),api.analytics()]);
        const studentData=r[0].status==="fulfilled"?r[0].value:[];
        const sessionalData=r[3].status==="fulfilled"?r[3].value:[];
        if(r[0].status==="fulfilled")setStudents(studentData);
        if(r[1].status==="fulfilled")setAttendance(r[1].value);
        if(r[2].status==="fulfilled")setAssignments(r[2].value);
        if(r[3].status==="fulfilled"){
            const names=new Map(studentData.map(s=>[s.rollNo,s.name]));
            const existing=new Set(sessionalData.map(x=>x.rollNo));
            const existingWithNames=sessionalData.map(x=>({...x,studentName:names.get(x.rollNo)||x.rollNo}));
            const missing=studentData.filter(s=>!existing.has(s.rollNo)).map(s=>({
                id:`local-${s.rollNo}`,rollNo:s.rollNo,studentName:s.name,subjectCode:SUBJECT.code,subjectName:SUBJECT.name,s1:0,s2:0,s3:0,total:0,status:"Pending"
            }));
            setSessionals([...existingWithNames,...missing]);
        }
        if(r.some(x=>x.status==="rejected"))setError("Some live data could not be loaded. Check that the Java backend is running.");
        setLoading(false);
    };
    useEffect(()=>{load();},[]);

    const attendanceRows=students.map(s=>{
        const existing=attendance.find(a=>a.rollNo===s.rollNo);
        return existing||{rollNo:s.rollNo,studentName:s.name,section:s.section,status:"Present"};
    });
    const setAllStatus=status=>setAttendance(attendanceRows.map(x=>({...x,status})));

    const saveAttendance=async()=>{
        try{
            for(const x of attendanceRows){
                const payload={...x,course:"BCA",subjectCode:SUBJECT.code,subjectName:SUBJECT.name,date:new Date().toISOString().slice(0,10),markedBy:user.username,time:new Date().toLocaleTimeString()};
                if(x.id)await api.updateAttendance(x.id,payload); else await api.saveAttendance(payload);
            }
            setToast("Attendance saved to MySQL successfully");await load();
        }catch(e){setToast(e.message);}
    };

    const saveSessionals=async()=>{
        try{
            for(const x of sessionals){
                const payload={rollNo:x.rollNo,subjectCode:x.subjectCode,subjectName:x.subjectName,s1:x.s1||0,s2:x.s2||0,s3:x.s3||0};
                if(String(x.id).startsWith("local-")) await api.saveSessional(payload);
                else await api.updateSessional(x.id,payload);
            }
            setToast("Sessional marks saved");await load();
        }catch(e){setToast(e.message);}
    };

    const nav=[["dashboard","🏠","Dashboard"],["attendance","📅","Mark Attendance"],["reports","📊","Reports & Analytics"],["assignments","📝","Assignments"],["sessionals","🎯","Sessionals"],["notifications","🔔","Notifications"],["profile","👤","Profile"]];

    return <div className={`portal faculty-portal ${dark?"dark":""}`}>
        <aside className="portal-sidebar"><div className="portal-brand">🎓<div><b>College ERP</b><small>Faculty Portal</small></div></div><nav>{nav.map(n=><button className={page===n[0]?"active":""} onClick={()=>setPage(n[0])} key={n[0]}><span>{n[1]}</span>{n[2]}</button>)}</nav><div className="sidebar-user"><div className="avatar">{(user.fullName||"FC").slice(0,2).toUpperCase()}</div><div><b>{user.fullName}</b><small>Faculty</small></div></div><button className="sidebar-logout" onClick={onLogout}>⇥ Logout</button></aside>
        <main className="portal-main"><header className="portal-topbar"><div><h1>{nav.find(n=>n[0]===page)?.[2]}</h1><small>College ERP / Faculty Portal</small></div><div className="top-actions"><button onClick={()=>setDark(!dark)}>{dark?"☀️":"🌙"}</button><button className="bell" onClick={()=>setNotifications(true)}>🔔</button><button className="top-user" onClick={()=>setPage("profile")}><span className="avatar small">{(user.fullName||"FC").slice(0,2).toUpperCase()}</span>{user.fullName} ▾</button></div></header>
        <div className="portal-content">{error&&<div className="error-box">{error}<button className="outline" onClick={load}>Retry</button></div>}{loading?<div className="card loading-state">Loading live ERP data…</div>:<>
            {page==="dashboard"&&<Dashboard analytics={analytics} setPage={setPage}/>}
            {page==="attendance"&&<AttendancePage rows={attendanceRows} setRows={setAttendance} save={saveAttendance}/>}
            {page==="reports"&&<Reports analytics={analytics}/>}
            {page==="assignments"&&<Assignments assignments={assignments} onAdd={()=>setModal({type:"assignment"})} onEdit={x=>setModal({type:"assignment",item:x})} onDelete={async x=>{try{await api.deleteAssignment(x.id);setToast("Assignment deleted");load();}catch(e){setToast(e.message)}}}/>}
            {page==="sessionals"&&<Sessionals rows={sessionals} setRows={setSessionals} save={saveSessionals}/>}
            {page==="notifications"&&<section className="card"><Head title="Notifications" sub="Faculty alerts and system announcements"/><button className="primary" onClick={()=>setNotifications(true)}>🔔 Open Notification Center</button></section>}
            {page==="profile"&&<section className="card"><Head title="Faculty Profile" sub="Account and department information"/><div className="profile-large"><div className="avatar xl">{(user.fullName||"FC").slice(0,2).toUpperCase()}</div><div><h2>{user.fullName}</h2><p>{user.email}</p></div></div><div className="form-grid"><Field label="Faculty ID" value={user.username}/><Field label="Department" value={user.department||"Computer Science"}/><Field label="Role" value="Faculty"/><Field label="Status" value="Active"/></div></section>}
        </>}</div></main>
        {notifications&&<Notifications username={user.username} onClose={()=>setNotifications(false)}/>}
        {modal&&<AssignmentModal item={modal.item} onClose={()=>setModal(null)} onSaved={()=>{setModal(null);setToast("Assignment saved");load();}}/>}
        {toast&&<Toast message={toast} onClose={()=>setToast("")}/>}
    </div>;
}

function Dashboard({analytics,setPage}){const pct=analytics?.attendancePercentage||0;return <><section className="welcome"><div><span>FACULTY PORTAL</span><h2>Manage academic records</h2><p>Attendance, assignments and sessionals are connected to the Java backend.</p></div></section><section className="stat-grid"><Stat icon="👨‍🎓" label="Students" value={analytics?.totalStudents||0} tone="green"/><Stat icon="📅" label="Attendance" value={`${pct}%`} tone="orange"/><Stat icon="✓" label="Present" value={analytics?.present||0} tone="blue"/><Stat icon="✕" label="Absent" value={analytics?.absent||0} tone="red"/></section><div className="two-col"><div className="card"><Head title="Quick Actions" sub="Open a working module"/><div className="action-grid">{[["attendance","📅","Mark Attendance"],["assignments","📝","Assignments"],["sessionals","🎯","Sessionals"],["reports","📊","Reports"]].map(x=><button key={x[0]} onClick={()=>setPage(x[0])}><span>{x[1]}</span><b>{x[2]}</b></button>)}</div></div><div className="card"><Head title="Attendance Trend" sub="Last six months"/><LineChart data={analytics?.monthly||[]}/></div></div></>}

function AttendancePage({rows,setRows,save}){return <section className="card"><Head title="Mark Attendance" sub="Course: BCA • Semester 4 • Section A" action={<button className="outline" onClick={()=>setRows(rows.map(x=>({...x,status:"Present"})))}>Mark All Present</button>}/><div className="module-actions"><button className="success" onClick={()=>setRows(rows.map(x=>({...x,status:"Present"})))}>✓ Present All</button><button className="danger" onClick={()=>setRows(rows.map(x=>({...x,status:"Absent"})))}>✕ Absent All</button><button className="primary" onClick={save}>💾 Save Attendance</button></div><table><thead><tr><th>Roll No</th><th>Student</th><th>Section</th><th>Status</th><th>Action</th></tr></thead><tbody>{rows.map(x=><tr key={x.rollNo}><td>{x.rollNo}</td><td><b>{x.studentName}</b></td><td>{x.section}</td><td><span className={x.status==="Present"?"status-good":"status-bad"}>{x.status}</span></td><td><button className="outline" onClick={()=>setRows(rows.map(r=>r.rollNo===x.rollNo?{...r,status:r.status==="Present"?"Absent":"Present"}:r))}>{x.status==="Present"?"Mark Absent":"Mark Present"}</button></td></tr>)}</tbody></table></section>}

function Reports({analytics}){return <section className="card"><Head title="Reports & Analytics" sub="Live college-wide attendance analytics"/><div className="report-cards"><Stat label="Students" value={analytics?.totalStudents||0} tone="blue"/><Stat label="Faculty" value={analytics?.totalFaculty||0} tone="green"/><Stat label="Present" value={analytics?.present||0} tone="green"/><Stat label="Absent" value={analytics?.absent||0} tone="red"/><Stat label="Attendance" value={`${analytics?.attendancePercentage||0}%`} tone="purple"/></div><LineChart data={analytics?.monthly||[]}/></section>}

function Assignments({assignments,onAdd,onEdit,onDelete}){return <section className="card"><Head title="Assignments" sub="Create and manage assignments" action={<button className="primary" onClick={onAdd}>＋ Add Assignment</button>}/>{assignments.length?<table><thead><tr><th>Code</th><th>Subject</th><th>Type</th><th>Max</th><th>Obtained</th><th>Actions</th></tr></thead><tbody>{assignments.map(a=><tr key={a.id}><td>{a.subjectCode}</td><td>{a.subjectName}</td><td>{a.type}</td><td>{a.maxMarks}</td><td>{a.obtainedMarks??"-"}</td><td><button onClick={()=>onEdit(a)}>✏️ Edit</button><button onClick={()=>onDelete(a)}>🗑 Delete</button></td></tr>)}</tbody></table>:<div className="empty-state">No assignments yet. Add the first assignment.</div>}</section>}

function Sessionals({rows,setRows,save}){return <section className="card"><Head title="Sessionals" sub="Enter S1, S2 and S3 marks" action={<button className="primary" onClick={save}>💾 Save Sessionals</button>}/>{rows.length?<table><thead><tr><th>Roll No</th><th>Student</th><th>Subject</th><th>S1</th><th>S2</th><th>S3</th><th>Total</th><th>Status</th></tr></thead><tbody>{rows.map(x=>{const total=(+x.s1||0)+(+x.s2||0)+(+x.s3||0);return <tr key={x.id}><td>{x.rollNo}</td><td>{x.studentName||x.rollNo}</td><td>{x.subjectCode}</td>{["s1","s2","s3"].map(k=><td key={k}><input className="mark-input" type="number" min="0" max="20" value={x[k]??0} onChange={e=>setRows(rows.map(r=>r.id===x.id?{...r,[k]:e.target.value}:r))}/></td>)}<td><b>{total}/60</b></td><td>{total===0?"Pending":total>=30?"Pass":"Needs Review"}</td></tr>})}</tbody></table>:<div className="empty-state">No sessional rows yet. Create marks from the API/Admin tools first.</div>}</section>}

function AssignmentModal({item,onClose,onSaved}){const [f,setF]=useState(item||{section:"A",subjectCode:"BCA301",subjectName:"Operating Systems",type:"Theory",credits:4,maxMarks:20,obtainedMarks:0,details:""});const save=async()=>{try{if(item)await api.updateAssignment(item.id,f);else await api.saveAssignment(f);onSaved();}catch(e){alert(e.message)}};return <Modal title={item?"Edit Assignment":"Add Assignment"} onClose={onClose}><div className="form-grid">{[["section","Section"],["subjectCode","Subject Code"],["subjectName","Subject Name"],["type","Type"],["credits","Credits"],["maxMarks","Max Marks"],["obtainedMarks","Obtained Marks"],["details","Details"]].map(([k,l])=><label className="field" key={k}><span>{l}</span><input value={f[k]??""} onChange={e=>setF({...f,[k]:e.target.value})}/></label>)}</div><button className="primary" onClick={save} style={{marginTop:18}}>Save Assignment</button></Modal>}

function Stat({icon,label,value,tone}){return <div className={`stat ${tone||""}`}><span className="stat-icon">{icon||"◈"}</span><div><small>{label}</small><strong>{value}</strong></div></div>}
function Head({title,sub,action}){return <div className="card-head"><div><h3>{title}</h3><p>{sub}</p></div>{action}</div>}
function Field({label,value}){return <label className="field"><span>{label}</span><input value={value??""} readOnly/></label>}
