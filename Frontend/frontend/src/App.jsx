import SignIn_Page from './SignIn_Page';
import Main_Page from "./Main_Page";
import "./CSS/App.css";
import { useState } from 'react';


function App() {
  const [ifLogIn, setIfLogIn] = useState(false);

  return (
    <>
      { !ifLogIn &&
        (<div className="background">
          <SignIn_Page
            setIfLogIn = {setIfLogIn}
          />
        </div>)
      }
      {
        ifLogIn && (
        <Main_Page/>
        )
      }
    </>
  )
}

export default App
