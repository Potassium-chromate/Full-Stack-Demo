import { useState } from 'react'
import axios from "axios";
import "./CSS/SignIn_page.css"

function SignIn_Page({setIfLogIn}) {
  const [identiftInfo, setIdentiftInfo] = useState({"account": "", "password": ""});

  const handleInputChange =(e) => {
    const {name, value} = e.target
    setIdentiftInfo({
      ...identiftInfo,
      [name]: value
    })
  }

  const handleLogin =() => {
    console.info(identiftInfo)
     axios.post("http://localhost:8080/login", {
            username: identiftInfo.account,
            password: identiftInfo.password
        },
        {withCredentials: true})
        .then(response => {
            console.log("Log in successfully");
            alert("Log in successfully");
            setIfLogIn(true);
            return true;
        })
        .catch(error => {
            console.error("API request error: ", error.response.data);
            alert(error.response.data);
            return false;
	    }
        );
  }

  const handleSignUp =() => {
    console.info(identiftInfo)
     axios.post("http://localhost:8080/signup", {
            username: identiftInfo.account,
            password: identiftInfo.password,
            role: "USER"
        })
        .then(response => {
            console.log("Sign up successfully");
            alert("Sign up successfully");
            return true;
        })
        .catch(error => {
            console.error("API request error: ", error.response.data);
            alert(error.response.data);
            return false;
        });
  }

  return (
    <>
      <div className='signin_page'>
        <div className='signin_content'>
            <h1>User Login</h1>
            <label>Account</label>
            <input type='text' name="account" onChange={handleInputChange}/>
            <label>Passwor</label>
            <input type='password' name='password' onChange={handleInputChange}/>
            <button onClick={handleLogin}>Login</button>
            <button onClick={handleSignUp}>Sign up</button>
        </div>
      </div>
    </>  
  )
}

export default SignIn_Page
