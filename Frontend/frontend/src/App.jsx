import { useState } from 'react'
import axios from "axios";

function App() {
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
     axios.post("http://127.0.0.1:8080/login", {
            account: identiftInfo.account,
            password: identiftInfo.password
        })
        .then(response => {
            console.log("Log in successfully");
            return true;
        })
        .catch(error => {
            console.error("API request error: ", error.message);
	    if (error.response) {
	      console.error("Response error: ", error.response);
	    }
            return false;
        });
  }

  return (
    <>
      Account: <input type='text' name="account" onChange={handleInputChange}/>
      Passwor: <input type='password' name='password' onChange={handleInputChange}/>
      <button onClick={handleLogin}>Login</button>
    </>  
  )
}

export default App
