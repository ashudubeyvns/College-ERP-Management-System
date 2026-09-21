# Bug Fix Implementation Plan

## Issues Found:
1. **FacultyDashboard passes `null` for navigation callbacks** - All module buttons (Home, Attendance) fail silently
2. **StudentDashboard** - Quick action buttons have no proper navigation to modules
3. **Scroller issues** - Some modules may have sizing/layout issues in FacultyDashboard

## Steps:

### Step 1: Create `DashboardCallback` interface ✅
- Create a callback interface with `showHome()` and `showAttendance()` methods
- Created `DashboardCallback.java`

### Step 2: Update module panels to use `DashboardCallback` ⏳
- `MarkAttendance.java` - Update `getPanel()` signature
- `ViewAttendance.java` - Update `getPanel()` signature
- `AssignmentModule.java` - Update `getPanel()` signature
- `SessionalsModule.java` - Update `getPanel()` signature
- `Change_Password.java` - Update `getPanel()` signature

### Step 3: Update `HomeScreen.java` to implement `DashboardCallback` ⏳
- Implement the interface
- No method changes needed (already has `showHome()` and `showAttendance()`)

### Step 4: Fix `FacultyDashboard.java` ⏳
- Pass a proper callback instead of null to module panels
- Ensure navigation buttons work correctly

### Step 5: Fix `StudentDashboard.java` ⏳
- Add proper module navigation for quick action buttons

### Step 6: Fix scrollable area issues in modules ⏳
- Ensure proper preferred size is set for modules embedded in FacultyDashboard

### Step 7: Build and Test ⏳
- Compile and verify all fixes
