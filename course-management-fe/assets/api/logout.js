function removeToken() {
    window.localStorage.removeItem('access_token');
    window.localStorage.removeItem('username');
    window.sessionStorage.removeItem('username');
    window.location.replace("http://127.0.0.1:5500/login.html")
        //location.reload()
}