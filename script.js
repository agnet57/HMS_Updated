// Mock patient data
let patients = [];

// Login Validation
document.getElementById('loginForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const userId = document.getElementById('userId').value;
    const password = document.getElementById('password').value;
    const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{10,}$/;

    if (userId.length < 8) {
        showMessage('loginMessage', 'User ID must be at least 8 characters.', true);
    } else if (!passwordRegex.test(password)) {
        showMessage('loginMessage', 'Password must be at least 10 characters with 1 uppercase, 1 number, and 1 special character.', true);
    } else {
        showMessage('loginMessage', 'Login successful!');
        setTimeout(() => {
            document.getElementById('login').style.display = 'none';
            document.getElementById('dashboard').style.display = 'block';
        }, 1000);
    }
});

// Logout Functionality
function logout() {
    showMessage('loginMessage', 'Logged out successfully!');
    document.getElementById('dashboard').style.display = 'none';
    document.getElementById('login').style.display = 'block';
    patients = []; // Clear patient data
    updatePatientTable(); // Clear table
    document.getElementById('loginForm').reset(); // Reset login form
    document.getElementById('patientForm').reset(); // Reset patient form
    document.getElementById('searchId').value = ''; // Clear search input
    document.getElementById('billId').value = ''; // Clear billing input
    document.getElementById('searchResult').textContent = ''; // Clear search result
    document.getElementById('billResult').textContent = ''; // Clear billing result
    document.getElementById('manageMessage').textContent = ''; // Clear manage message
}

// Tab Switching
function openTab(tabId) {
    document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));
    document.querySelectorAll('.tab-button').forEach(btn => btn.classList.remove('active'));
    document.getElementById(tabId).classList.add('active');
    document.querySelector(`button[onclick="openTab('${tabId}')"]`).classList.add('active');
}

// Patient Management
document.getElementById('patientForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const patientId = document.getElementById('patientId').value;
    const patientName = document.getElementById('patientName').value;
    const roomNumber = document.getElementById('roomNumber').value;

    const existingPatient = patients.find(p => p.id === patientId);
    if (existingPatient) {
        existingPatient.name = patientName;
        existingPatient.room = roomNumber;
        showMessage('manageMessage', 'Patient updated successfully!');
    } else {
        patients.push({ id: patientId, name: patientName, room: roomNumber });
        showMessage('manageMessage', 'Patient added successfully!');
    }
    updatePatientTable();
    this.reset();
});

function deletePatient() {
    const patientId = document.getElementById('patientId').value;
    const index = patients.findIndex(p => p.id === patientId);
    if (index !== -1) {
        patients.splice(index, 1);
        showMessage('manageMessage', 'Patient deleted successfully!');
        updatePatientTable();
    } else {
        showMessage('manageMessage', 'Patient not found.', true);
    }
}

function updatePatientTable() {
    const tbody = document.getElementById('patientList');
    tbody.innerHTML = '';
    patients.forEach(p => {
        const row = `<tr><td>${p.id}</td><td>${p.name}</td><td>${p.room}</td></tr>`;
        tbody.innerHTML += row;
    });
}

// Search Patient
function searchPatient() {
    const searchId = document.getElementById('searchId').value;
    const patient = patients.find(p => p.id === searchId);
    if (patient) {
        showMessage('searchResult', `Found: ${patient.name}, Room: ${patient.room}`);
    } else {
        showMessage('searchResult', 'Patient not found.', true);
    }
}

// Generate Bill (Mock)
function generateBill() {
    const billId = document.getElementById('billId').value;
    const patient = patients.find(p => p.id === billId);
    if (patient) {
        const bill = `Bill for ${patient.name}: Room: $1000, Pharmacy: $500, Diagnostics: $300, Total: $1800`;
        showMessage('billResult', bill);
    } else {
        showMessage('billResult', 'Patient not found.', true);
    }
}

// Utility Function for Messages
function showMessage(elementId, message, isError = false) {
    const element = document.getElementById(elementId);
    element.textContent = message;
    element.className = `message ${isError ? 'error' : ''}`;
}
