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
      if(err) console.log(err);
      if(res.length==0){
      console.log("User: "+name+" with password: "+pw+" is not in database");
      let attempt="INSERT INTO loginhistory VALUES(id,(SELECT id FROM loginclient WHERE login like '"+name+"'),CURRENT_TIMESTAMP,0);";
      con.query(attempt,(req)=>{
        if (err) console.log(err);
      });

      callbackL(401,"Incorrect Username or password!");
      }
      else{
        for(let i=0;i<tokens.length;i++){
          if(tokens[i].username=name){
            tokens.splice(i,1);
          }          
        }
        let id=res[0].id;
        console.log("User: "+name+" with password: "+pw+" is in database");
        let blocksql="SELECT * FROM loginhistory WHERE idl="+id+" ORDER BY logdate desc LIMIT 3;";
        con.query(blocksql,(err,res)=>{
          if(err) console.log(err);
          console.log("ehm nejde"+res);
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
            callbackL(401,"LUL ma 3 po sebe špatne");
          }
        });
      }
      con.end();
      console.log(tokens);
    });
  }); 
});

//posielame json v ktorom je username a token , ak je spravny nereturnne nič iba 200 kod a hlašku logged off

app.post('/logout',(req,res,callbackout)=>{
  console.log("Request on /logout");

  callbackout=function(status,value){
    res.status(status).send(value);
  };
  
  let name=req.body.username;
  let token=req.body.token;
  let obj=new Object();
  obj.name=name;
  obj.token=token;
  for(let i=0;i<tokens.length;i++){
    if((tokens[i].username=name) && (tokens[i].token=token)){
      tokens.splice(i,1);
      callbackout(200,"Logout Successful");
      break;
    }
    else{
      callbackout(401,"Wrong tokens!");
      break;
    }
  }
  console.log(tokens);
});

app.listen(3000);