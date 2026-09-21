const API_BASE = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";

async function request(path, options = {}) {
    const res = await fetch(`${API_BASE}${path}`, {
        headers: {"Content-Type":"application/json", ...(options.headers || {})},
        ...options
    });
    const text = await res.text();
    let data = {};
    try { data = text ? JSON.parse(text) : {}; } catch { data = {raw:text}; }
    if (!res.ok) throw new Error(data.message || `Request failed (${res.status})`);
    return data;
}

export const api = {
    base: API_BASE,
    login:(username,password,role)=>request("/auth/login",{method:"POST",body:JSON.stringify({
        username,password,role:role.toUpperCase()==="ADMINISTRATOR"?"ADMIN":role.toUpperCase()
    })}),

    students:()=>request("/students"),
    student:(rollNo)=>request(`/students/${encodeURIComponent(rollNo)}`),
    addStudent:(s)=>request("/students",{method:"POST",body:JSON.stringify(s)}),
    updateStudent:(id,s)=>request(`/students/${id}`,{method:"PUT",body:JSON.stringify(s)}),
    updateStudentProfile:(rollNo,s)=>request(`/students/profile/${encodeURIComponent(rollNo)}`,{method:"PUT",body:JSON.stringify(s)}),
    deleteStudent:(id)=>request(`/students/${id}`,{method:"DELETE"}),

    faculty:()=>request("/faculty"),
    addFaculty:(f)=>request("/faculty",{method:"POST",body:JSON.stringify(f)}),
    updateFaculty:(id,f)=>request(`/faculty/${id}`,{method:"PUT",body:JSON.stringify(f)}),
    deleteFaculty:(id)=>request(`/faculty/${id}`,{method:"DELETE"}),

    attendance:(roll)=>request(`/attendance${roll?`?rollNo=${encodeURIComponent(roll)}`:""}`),
    todayAttendance:(subjectCode)=>request(`/attendance/today?subjectCode=${encodeURIComponent(subjectCode)}`),
    saveAttendance:(a)=>request("/attendance",{method:"POST",body:JSON.stringify(a)}),
    updateAttendance:(id,a)=>request(`/attendance/${id}`,{method:"PUT",body:JSON.stringify(a)}),
    deleteAttendance:(id)=>request(`/attendance/${id}`,{method:"DELETE"}),

    assignments:()=>request("/assignments"),
    saveAssignment:(a)=>request("/assignments",{method:"POST",body:JSON.stringify(a)}),
    updateAssignment:(id,a)=>request(`/assignments/${id}`,{method:"PUT",body:JSON.stringify(a)}),
    deleteAssignment:(id)=>request(`/assignments/${id}`,{method:"DELETE"}),

    sessionals:(rollNo)=>request(`/sessionals${rollNo?`?rollNo=${encodeURIComponent(rollNo)}`:""}`),
    saveSessional:(s)=>request("/sessionals",{method:"POST",body:JSON.stringify(s)}),
    updateSessional:(id,s)=>request(`/sessionals/${id}`,{method:"PUT",body:JSON.stringify(s)}),
    deleteSessional:(id)=>request(`/sessionals/${id}`,{method:"DELETE"}),

    notifications:(u)=>request(`/notifications/${encodeURIComponent(u)}`),
    readNotification:(id)=>request(`/notifications/${id}/read`,{method:"POST"}),

    analytics:()=>request("/analytics"),
    studentAnalytics:(rollNo)=>request(`/analytics/student/${encodeURIComponent(rollNo)}`)
};
