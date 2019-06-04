function login(){
    let name=document.getElementById("userName").value;
    let password=document.getElementById("pass").value;
    let xhr=new XMLHttpRequest();
    xhr.onreadystatechange=function(){
        console.log(this);
        if(this.readyState!==4 && this.status!==200){
            let response=this.responseText;
            document.getElementById("errorWrap").innerHTML=response.message;
        }
        else{
            localStorage.setItem('token',this.responseText);
            window.location.href("./main.html");
        }
    }
    xhr.open("POST","http://localhost:3000/login");
    xhr.setRequestHeader('Content-Type','application/json');
    xhr.send(JSON.stringify({username:name,password:password}));
}