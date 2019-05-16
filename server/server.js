const http=require('http');
const express = require('express');
const mysql = require('mysql');
const TokenGenerator = require('uuid-token-generator');
const cors=require('cors');

let tokens=[];

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


  let con=mysql.createConnection({
    host: "itsovy.sk",
    user: "glbank",
    password: "password",
    database: "glbank",
    port: "3306"
  });

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
      con.end();
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
  tokens=tokens.filter(person => person.username != name);
  let obj=new Object();
  obj.name=name;
  obj.token=token;
  let mess=new Object;
  mess.message="User has been logged off!";
  callbackout(200,JSON.stringify(mess));
  console.log(tokens);
});

app.post('/userinfo',(req,res,callbackUI)=>{
  console.log("Request on /userinfo");

  callbackUI=function(status,value){
    res.status(status).send(value);
  }

  let name=req.body.username;
  let token=req.body.token;
  let obj=new Object();
  obj.name=name;
  obj.token=token;
  for(let i=0;i<tokens.length;i++){
    if(tokens[i]==obj){
      let sql=" SELECT fname,lname,email from client INNER JOIN loginclient on client.id=loginclient.idc where login like '"+name+"';";
      con.query(sql,(err,res)=>{
        if(err) console.log(err);
        if(res.length==0){
          console.log("User: "+name+" with password: "+pw+" is not in database");
          let mess=new Object;
          mess.message="User has been blocked!";
          callbackUI(401,JSON.stringify(mess));
        }
        else{

        }
      });


      callbackUI(200,);
      break;
    }
  }
});


app.listen(3000);