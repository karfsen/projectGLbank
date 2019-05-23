const http=require('http');
const express = require('express');
const mysql = require('mysql');
const TokenGenerator = require('uuid-token-generator');
const cors=require('cors');

let tokens=[];


let con=mysql.createConnection({
  host: "itsovy.sk",
  user: "glbank",
  password: "password",
  database: "glbank",
  port: "3306"
});

//Definuje token generaciu(pomocou knižnice na tokeny)
const tokgen = new TokenGenerator(128, TokenGenerator.BASE62);
//console.log(tokgen);
var app=express();
app.use(express.json());
app.use(cors());


//posielame JSON username password ak je spravny returnne JSON s username a token

app.post('/login',(req,res,callbackL)=>{
  console.log("Request on /login");
  callbackL=function(status,value){
    res.status(status).send(value);
    console.log(tokens);
  };

  let name=req.body.username;
  let pw=req.body.password;
  let token="";

  con.connect((err)=>{

    if (err) console.log(err);            
    //console.log("connected");
      
    let sql="SELECT id,login,password FROM loginclient "+
    "WHERE login like '"+name+"' "+      
    "and password like md5('"+pw+"');";
    //console.log(sql);

    con.query(sql,(err,res)=>{
      console.log(res);
      if(err) console.log(err);
      if(res.length==0){
      console.log("User: "+name+" with password: "+pw+" is not in database");
      let attempt="INSERT INTO loginhistory VALUES(id,(SELECT id FROM loginclient WHERE login like '"+name+"'),CURRENT_TIMESTAMP,0);";
      con.query(attempt,(req)=>{
        if (err) console.log(err);
      });
      let mess=new Object;
      mess.message="User with this password doesn't exist!";
      callbackL(401,JSON.stringify(mess));
      }
      else{
        tokens=tokens.filter(person => person.username != name);
        let id=res[0].id;
        console.log(id);
        console.log("User: "+name+" with password: "+pw+" is in database");
        let blocksql="SELECT * FROM loginhistory WHERE idl="+id+" ORDER BY logdate desc LIMIT 3;";
        con.query(blocksql,(err,res)=>{
          //console.log(res.body);
          if(err) console.log(err);
          console.log(res);
          if(res.length==0 || res[0].Success==1 || (res[0].Success==0 && res[1].Success==1) || (res[0].Success==0 && res[1].Success==0 && res[2].Success==1)){
            let attempt="INSERT INTO loginhistory VALUES(id,"+id+",CURRENT_TIMESTAMP,1);";
            con.query(attempt,()=>{
              if (err) console.log(err);
            });
            token=tokgen.generate();
            let obj=new Object();
            obj.username=name;
            obj.token=token;
            tokens.push(obj);          
            callbackL(200,JSON.stringify(obj));
          }
          else{
            let mess=new Object;
            mess.message="User has been blocked!";
            callbackL(401,JSON.stringify(mess));
          }
        });
      }
    });
  }); 
});

//posielame json v ktorom je username a token , ak je spravny returnne 200 a json s messagom

app.post('/logout',(req,res,callbackout)=>{
  console.log("Request on /logout");

  callbackout=function(status,value){
    res.status(status).send(value);
  };
  let name=req.body.username;
  let token=req.body.token;
  if(tokens.find(person => (person.username ==name && person.token==token))){
    tokens=tokens.filter(person => (person.username != name && person.token !=token));
    let mess=new Object;
    mess.message="User has been logged off!";
    callbackout(200,JSON.stringify(mess));
  }
  else{
    let mess=new Object;
    mess.message="Wrong user token!";
    callbackout(401,JSON.stringify(mess));
  }
  console.log(tokens);
});

app.post('/userinfo',(req,res,callbackUI)=>{
  console.log("Request on /userinfo");

  callbackUI=function(status,value){
    res.status(status).send(value);
  }

  let name=req.body.username;
  let token=req.body.token;
  console.log(name+" "+token);
  if(tokens.find(person => (person.username ==name && person.token==token))){
    let sql="SELECT fname,lname,email from client INNER JOIN loginclient on client.id=loginclient.idc where login like '"+name+"';";
    con.query(sql,(err,res)=>{
      if(err) console.log(err);
      if(res.length==0){
        console.log("User: "+name+" with token: "+token+" is not logged in");
        let mess=new Object;
        mess.message="Could not get user info!";
        callbackUI(403,JSON.stringify(mess));
      }
      else{
        console.log("User: "+name+" with token: "+token+" is not logged in");
        let obj=res[0];
        callbackUI(200,JSON.stringify(obj));
      }
    });
  }
  else{
    callbackUI(401,JSON.stringify({message:"Wrong user credintials!"}));
  }
});

app.post('/accounts',(req,res,callbackUI)=>{
  console.log("Request on /accounts");

  callbackUI=function(status,value){
    res.status(status).send(value);
  }

  let name=req.body.username;
  let token=req.body.token;
  console.log(name+" "+token);
  if(tokens.find(person => (person.username ==name && person.token==token))){
    let sql="SELECT AccNum from account INNER JOIN client on client.id=account.idc inner join loginclient on client.id=loginclient.idc where login like '"+name+"';";
    con.query(sql,(err,res)=>{
      if(err) console.log(err);
      if(res.length==0){
      console.log("User: "+name+" with token: "+token+" is not logged in");
      let mess=new Object;
      mess.message="Could not get user accounts!";
      callbackUI(403,JSON.stringify(mess));
      }
      else{
        console.log("User: "+name+" with token: "+token+" is logged in");
        callbackUI(200,JSON.stringify(res));
      }
    });
  }
  else{
    callbackUI(401,JSON.stringify({message:"Wrong user credintials!"}));
  }
});

app.post('/accinfo',(req,res,callbackUI)=>{
  console.log("Request on /accinfo");

  callbackUI=function(status,value){
    res.status(status).send(value);
  }

  let name=req.body.username;
  let token=req.body.token;
  let accnum=req.body.accnum
  console.log(name+" "+token);
  if(tokens.find(person => (person.username ==name && person.token==token))){
    let sql="SELECT * from account where AccNum='"+accnum+"';";
    con.query(sql,(err,res)=>{
      if(err) console.log(err);
      if(res.length==0){
      console.log("User: "+name+" with token: "+token+" is not logged in");
      let mess=new Object;
      mess.message="Could not get account info!";
      callbackUI(403,JSON.stringify(mess));
      }
      else{
        console.log("User: "+name+" with token: "+token+" is logged in");
        callbackUI(200,JSON.stringify(res));
      }
    });
  }
  else{
    callbackUI(401,JSON.stringify({message:"Wrong user credintials!"}));
  }
});


app.post('/cards',(req,res,callbackc)=>{
  console.log("Request on /card");

  callbackc=function(status,value){
    res.status(status).send(value);
  }

  let name=req.body.username;
  let token=req.body.token;
  let idAcc=req.body.idacc;

  console.log(name+" "+token);
  if(tokens.find(person => (person.username ==name && person.token==token))){
    let sql="select id from card where ida like'"+idAcc+"';";
    con.query(sql,(err,res)=>{
      if(err) console.log(err);
      if(res.length==0){
      console.log("User: "+name+" with token: "+token+" is not logged in");
      let mess=new Object;
      mess.message="User has no cards yet!";
      callbackc(403,JSON.stringify(mess));
      }
      else{
        console.log("User: "+name+" with token: "+token+" is logged in");
        callbackc(200,JSON.stringify(res));
      }
    });
  }
  else{
    callbackc(401,JSON.stringify({message:"Wrong user credintials!"}));
  }
});

app.post('/cardinfo',(req,res,callbackci)=>{
  console.log("Request on /cardinfo");

  callbackci=function(status,value){
    res.status(status).send(value);
  }

  let name=req.body.username;
  let token=req.body.token;
  let idCard=req.body.idCard;

  console.log(name+" "+token);
  if(tokens.find(person => (person.username ==name && person.token==token))){
    let sql="select Active,ExpireM,ExpireY from card where id like'"+idCard+"';";
    con.query(sql,(err,res)=>{
      if(err) console.log(err);
      if(res.length==0){
      console.log("User: "+name+" with token: "+token+" is not logged in");
      let mess=new Object;
      mess.message="This card doesnt exist!";
      callbackci(403,JSON.stringify(mess));
      }
      else{
        console.log("User: "+name+" with token: "+token+" is logged in");
        callbackci(200,JSON.stringify(res));
      }
    });
  }
  else{
    callbackci(401,JSON.stringify({message:"Wrong user credintials!"}));
  }
});

app.post('/cardtrans',(req,res,callbackct)=>{
  console.log("Request on /cardtrans");

  callbackct=function(status,value){
    res.status(status).send(value);
  }

  let name=req.body.username;
  let token=req.body.token;
  let idCard=req.body.idCard;

  console.log(name+" "+token);
  if(tokens.find(person => (person.username ==name && person.token==token))){
    let sql="select * from cardtrans where idCard like'"+idCard+"';";
    con.query(sql,(err,res)=>{
      if(err) console.log(err);
      if(res.length==0){
      console.log("User: "+name+" with token: "+token+" is not logged in");
      let mess=new Object;
      mess.message="Card transaction history is empty!";
      callbackct(403,JSON.stringify(mess));
      }
      else{
        console.log("User: "+name+" with token: "+token+" is logged in");
        callbackct(200,JSON.stringify(res));
      }
    });
  }
  else{
    callbackct(401,JSON.stringify({message:"Wrong user credintials!"}));
  }
});

app.post('/blockcard',(req,res,callbackbc)=>{
  console.log("Request on /blockcard");

  callbackbc=function(status,value){
    res.status(status).send(value);
  }

  let name=req.body.username;
  let token=req.body.token;
  let idCard=req.body.idCard;

  console.log(name+" "+token);
  if(tokens.find(person => (person.username ==name && person.token==token))){
    let sql="update cards set blocked=1 where id="+idCard+";";
    con.query(sql,(err,res)=>{
      if(err) console.log(err);
        console.log("User: "+name+" with token: "+token+" is logged in");
        callbackbc(200,JSON.stringify(res));
      });
  }
  else{
    callbackbc(401,JSON.stringify({message:"Wrong user credintials!"}));
  }
});

app.post('/transhistory',(req,res,callbackTH)=>{
  console.log("Request on /transhistory");

  callbackTH=function(status,value){
    res.status(status).send(value);
  }

  let name=req.body.username;
  let token=req.body.token;
  let id=req.body.idacc;
  let accnum=req.body.accnum;

  console.log(name+" "+token);
  if(tokens.find(person => (person.username ==name && person.token==token))){
    let sql="SELECT * from transaction where idacc="+id+" or recaccount="+accnum+" ;";
    con.query(sql,(err,res)=>{
      if(err) console.log(err);
      if(res.length==0){
      console.log("User: "+name+" with token: "+token+" is not logged in");
      let mess=new Object;
      mess.message="Transaction history is empty!";
      callbackTH(403,JSON.stringify(mess));
      }
      else{
        console.log("User: "+name+" with token: "+token+" is logged in");
        callbackTH(200,JSON.stringify(res));
      }
    });
  }
  else{
    callbackTH(401,JSON.stringify({message:"Wrong user credintials!"}));
  }
});

app.post('/changepassword',(req,res,callbackchpw)=>{
  console.log("Request on /changepassword");

  callbackchpw=function(status,value){
    res.status(status).send(value);
  }

  let name=req.body.username;
  let token=req.body.token;
  let oldpw=req.body.oldpassword;
  let newpw=req.body.newpassword;

  console.log(name+" "+token);
  if(tokens.find(person => (person.username ==name && person.token==token))){
    let sql="SELECT id,login,password FROM loginclient "+
    "WHERE login like '"+name+"' "+      
    "and password like md5('"+oldpw+"');";;
    con.query(sql,(err,res)=>{
      if(err) console.log(err);
      if(res.length==0){
      console.log("User: "+name+" with token: "+token+" is not logged in");
      let mess=new Object;
      mess.message="Wrong credintials";
      callbackchpw(403,JSON.stringify(mess));
      }
      else{
        console.log("User: "+name+" with token: "+token+" is logged in");
        let sql2="update loginclient set password='"+newpw+"' where name like '"+name+"';";
        con.query(sql2,(err)=>{
          if (err) console.log(err);
          callbackchpw(200,JSON.stringify({message:"Password changed succesfully!"}));
        });
      }
    });
  }
  else{
    callbackchpw(401,JSON.stringify({message:"Wrong user credintials!"}));
  }
});

app.listen(3000);