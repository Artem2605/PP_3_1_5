async function getAllUsers() {
    const res = await fetch('http://localhost:8080/api/users');
    const users = await res.json();

    console.log(users);
}

window.addEventListener('DOMContentLoaded', getAllUsers)