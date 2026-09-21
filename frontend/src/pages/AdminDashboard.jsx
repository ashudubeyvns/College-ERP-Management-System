import {useEffect,useMemo,useState} from "react";
import "./AdminDashboard.css";
import "../pages/StudentDashboard.css";
import {api} from "../api";
import {LineChart,DonutChart,Modal,Notifications,Toast} from "../components";

export default function AdminDashboard({onLogout}){
    const [dark,setDark]=useState(false),[page,setPage]=useState("dashboard");
    const [students,setStudents]=useState([]),[faculty,setFaculty]=useState([]),[attendance,setAttendance]=useState([]);
    const [analytics,setAnalytics]=useState(null),[notifications,setNotifications]=useState(false);
    const [modal,setModal]=useState(null),[toast,setToast]=useState(""),[loading,setLoading]=useState(true),[error,setError]=useState("");
    const [query,setQuery]=useState("");

    const load=async()=>{
        setLoading(true);setError("");
        const r=await Promise.allSettled([api.students(),api.faculty(),api.attendance(),api.analytics()]);
        if(r[0].status==="fulfilled")setStudents(r[0].value); else setStudents([]);
        if(r[1].status==="fulfilled")setFaculty(r[1].value); else setFaculty([]);
        if(r[2].status==="fulfilled")setAttendance(r[2].value); else setAttendance([]);
        if(r[3].status==="fulfilled")setAnalytics(r[3].value); else setAnalytics(null);
        if(r.some(x=>x.status==="rejected"))setError("Some live data could not be loaded. Check that the Java backend is running.");
        setLoading(false);
    };
    useEffect(()=>{load();},[]);

    const filteredStudents=useMemo(()=>students.filter(x=>JSON.stringify(x).toLowerCase().includes(query.toLowerCase())),[students,query]);
    const filteredFaculty=useMemo(()=>faculty.filter(x=>JSON.stringify(x).toLowerCase().includes(query.toLowerCase())),[faculty,query]);

    const nav=[["dashboard","🏠","Dashboard Overview"],["students","👨‍🎓","Students Management"],["faculty","👩‍🏫","Faculty Management"],["attendance","📅","Attendance Management"],["reports","📊","Reports & Analytics"],["notifications","🔔","Notifications"],["settings","⚙️","Settings"]];

    return <div className={`portal admin-portal ${dark?"dark":""}`}>
        <aside className="portal-sidebar"><div className="portal-brand">🎓<div><b>College ERP</b><small>Administrator Portal</small></div></div><nav>{nav.map(n=><button className={page===n[0]?"active":""} onClick={()=>setPage(n[0])} key={n[0]}><span>{n[1]}</span>{n[2]}</button>)}</nav><div className="sidebar-user"><div className="avatar">SA</div><div><b>System Administrator</b><small>Administrator</small></div></div><button className="sidebar-logout" onClick={onLogout}>⇥ Logout</button></aside>
        <main className="portal-main"><header className="portal-topbar"><div><h1>{nav.find(n=>n[0]===page)?.[2]}</h1><small>College ERP / Administrator</small></div><div className="top-actions"><button onClick={()=>setDark(!dark)}>{dark?"☀️":"🌙"}</button><button className="bell" onClick={()=>setNotifications(true)}>🔔</button><button className="top-user" onClick={()=>setPage("settings")}><span className="avatar small">SA</span>System Administrator ▾</button></div></header>
        <div className="portal-content">{error&&<div className="error-box">{error}<button className="outline" onClick={load}>Retry</button></div>}{loading?<div className="card loading-state">Loading live ERP data…</div>:<>
            {page==="dashboard"&&<Dashboard analytics={analytics} setPage={setPage}/>}
            {page==="students"&&<Management type="student" data={filteredStudents} query={query} setQuery={setQuery} onAdd={()=>setModal({type:"student"})} onEdit={x=>setModal({type:"student",item:x})} onDelete={async x=>{if(!confirm(`Delete student ${x.rollNo}?`))return;try{await api.deleteStudent(x.id);setToast("Student deleted");load();}catch(e){setToast(e.message)}}}/>}
            {page==="faculty"&&<Management type="faculty" data={filteredFaculty} query={query} setQuery={setQuery} onAdd={()=>setModal({type:"faculty"})} onEdit={x=>setModal({type:"faculty",item:x})} onDelete={async x=>{if(!confirm(`Delete faculty ${x.username}?`))return;try{await api.deleteFaculty(x.id);setToast("Faculty deleted");load();}catch(e){setToast(e.message)}}}/>}
            {page==="attendance"&&<AttendanceManagement records={attendance} onRefresh={load} onToast={setToast}/>}
            {page==="reports"&&<Reports analytics={analytics}/>}
            {page==="notifications"&&<section className="card"><Head title="Notification Center" sub="MongoDB-backed notifications"/><button className="primary" onClick={()=>setNotifications(true)}>🔔 Open Notifications</button></section>}
            {page==="settings"&&<section className="card"><Head title="System Settings" sub="Administrator controls"/><div className="settings-grid"><Setting title="Theme" desc="Switch between light and dark interface." action={<button className="primary" onClick={()=>setDark(!dark)}>{dark?"☀️ Light Mode":"🌙 Dark Mode"}</button>}/><Setting title="Database" desc="MySQL stores ERP records; MongoDB stores notifications and audit documents." action={<span className="status-good">Configured</span>}/><Setting title="System Status" desc="Frontend and Java API local environment." action={<span className="status-good">Operational</span>}/></div></section>}
        </>}</div></main>
        {notifications&&<Notifications username="admin" onClose={()=>setNotifications(false)}/>}
        {modal&&<AdminModal type={modal.type} item={modal.item} onClose={()=>setModal(null)} onSaved={()=>{setModal(null);load();setToast("Record saved successfully")}}/>}
        {toast&&<Toast message={toast} onClose={()=>setToast("")}/>}
    </div>;
}

function Dashboard({analytics,setPage}){return <><section className="welcome"><div><span>ADMINISTRATOR PORTAL</span><h2>Welcome back, System Administrator 👋</h2><p>Live overview of college attendance and academic records.</p></div></section><section className="stat-grid"><Stat icon="👨‍🎓" label="Total Students" value={analytics?.totalStudents||0} tone="blue"/><Stat icon="👩‍🏫" label="Faculty" value={analytics?.totalFaculty||0} tone="green"/><Stat icon="📅" label="Attendance" value={`${analytics?.attendancePercentage||0}%`} tone="orange"/><Stat icon="✓" label="Present" value={analytics?.present||0} tone="purple"/></section><div className="two-col"><div className="card"><Head title="Attendance Analytics" sub="College-wide live trend"/><LineChart data={analytics?.monthly||[]}/></div><div className="card"><Head title="Current Distribution" sub="Present vs absent"/><DonutChart present={analytics?.present||0} absent={analytics?.absent||0}/></div></div><section className="card"><Head title="Quick Actions" sub="Administration tools"/><div className="admin-actions">{[["students","👨‍🎓","Manage Students"],["faculty","👩‍🏫","Manage Faculty"],["attendance","📅","Attendance"],["reports","📊","Analytics"],["notifications","🔔","Notifications"]].map(x=><button key={x[0]} onClick={()=>setPage(x[0])}><span>{x[1]}</span><b>{x[2]}</b><small>Open module →</small></button>)}</div></section></>}

function Management({type,data,query,setQuery,onAdd,onEdit,onDelete}){const isS=type==="student";return <section className="card"><Head title={isS?"Students Management":"Faculty Management"} sub="CRUD records stored in MySQL" action={<button className="primary" onClick={onAdd}>＋ Add {isS?"Student":"Faculty"}</button>}/><div className="table-tools"><input value={query} placeholder={`Search ${isS?"student":"faculty"}...`} onChange={e=>setQuery(e.target.value)}/><button className="outline" onClick={()=>window.print()}>🖨 Print</button></div>{data.length?<table><thead><tr>{isS?<><th>Roll No</th><th>Name</th><th>Course</th><th>Semester</th><th>Section</th><th>Status</th></>:<><th>Username</th><th>Name</th><th>Department</th><th>Email</th><th>Status</th></>}<th>Actions</th></tr></thead><tbody>{data.map(x=><tr key={x.id}>{isS?<><td><b>{x.rollNo}</b></td><td>{x.name}</td><td>{x.course}</td><td>{x.semester}</td><td>{x.section}</td><td>{x.status||"Active"}</td></>:<><td>{x.username}</td><td>{x.name}</td><td>{x.department}</td><td>{x.email}</td><td>{x.status||"Active"}</td></>}<td><button onClick={()=>onEdit(x)}>✏️ Edit</button><button onClick={()=>onDelete(x)}>🗑 Delete</button></td></tr>)}</tbody></table>:<div className="empty-state">No records found.</div>}</section>}

function AttendanceManagement({records,onRefresh,onToast}){return <section className="card"><Head title="Attendance Management" sub="Live records from MySQL" action={<button className="primary" onClick={onRefresh}>🔄 Refresh</button>}/>{records.length?<table><thead><tr><th>Date</th><th>Roll No</th><th>Student</th><th>Subject</th><th>Status</th><th>Actions</th></tr></thead><tbody>{records.map(x=><tr key={x.id}><td>{x.date}</td><td>{x.rollNo}</td><td>{x.studentName}</td><td>{x.subjectName}</td><td>{x.status}</td><td><button onClick={async()=>{try{await api.updateAttendance(x.id,{...x,status:x.status==="Present"?"Absent":"Present"});onToast("Attendance updated");onRefresh();}catch(e){onToast(e.message)}}}>Toggle</button><button onClick={async()=>{if(!confirm("Delete this attendance record?"))return;try{await api.deleteAttendance(x.id);onToast("Attendance deleted");onRefresh();}catch(e){onToast(e.message)}}}>🗑</button></td></tr>)}</tbody></table>:<div className="empty-state">No attendance records found.</div>}</section>}

function Reports({analytics}){return <section className="card"><Head title="Reports & Analytics" sub="Live college-wide analytics"/><div className="report-cards"><Stat label="Students" value={analytics?.totalStudents||0} tone="blue"/><Stat label="Faculty" value={analytics?.totalFaculty||0} tone="green"/><Stat label="Present" value={analytics?.present||0} tone="green"/><Stat label="Absent" value={analytics?.absent||0} tone="red"/><Stat label="Attendance" value={`${analytics?.attendancePercentage||0}%`} tone="purple"/></div><LineChart data={analytics?.monthly||[]}/></section>}

function AdminModal({type,item,onClose,onSaved}){const isS=type==="student";const [f,setF]=useState(item||(isS?{rollNo:"",name:"",course:"BCA",semester:4,section:"A",email:"",status:"Active"}:{username:"",name:"",department:"Computer Science",email:"",status:"Active"}));const save=async()=>{try{if(isS){if(item)await api.updateStudent(item.id,f);else await api.addStudent(f)}else{if(item)await api.updateFaculty(item.id,f);else await api.addFaculty(f)}onSaved();}catch(e){alert(e.message)}};const fields=isS?[["rollNo","Roll No"],["name","Student Name"],["course","Course"],["semester","Semester"],["section","Section"],["email","Email"]]:[["username","Username"],["name","Faculty Name"],["department","Department"],["email","Email"]];return <Modal title={`${item?"Edit":"Add"} ${isS?"Student":"Faculty"}`} onClose={onClose}><div className="form-grid">{fields.map(([k,l])=><label className="field" key={k}><span>{l}</span><input value={f[k]??""} onChange={e=>setF({...f,[k]:e.target.value})}/></label>)}</div><button className="primary" onClick={save} style={{marginTop:18}}>Save Record</button></Modal>}

function Stat({icon,label,value,tone}){return <div className={`stat ${tone||""}`}><span className="stat-icon">{icon||"◈"}</span><div><small>{label}</small><strong>{value}</strong></div></div>}
function Head({title,sub,action}){return <div className="card-head"><div><h3>{title}</h3><p>{sub}</p></div>{action}</div>}
function Setting({title,desc,action}){return <div className="setting"><div><h3>{title}</h3><p>{desc}</p></div>{action}</div>}
