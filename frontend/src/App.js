import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { useSelector } from 'react-redux'
import {
  BrowserRouter,
  NavLink,
  Route,
  Routes
} from 'react-router-dom';

import HomePage from './pages/Home'
import DoesNotExistPage from './pages/NotExistPage';
import NavigationBar from './components/navbar/NavBar';

//To add your page 
/*
  Copy and paste on of the routes
  The path is the link that the use needs to get to page e.g.
  path='/hello', on browser you will need to type localhost:3000/hello
  component={myComponent} is whatever component you want to show on page
  <NavigationBar />
          <Route path='/login' component={Login} />
        <Route exact path="/*" component={DoesNotExistPage} />
*/
function App() {
  const auth = useSelector(state => state.auth);
 return (
  
  

  <BrowserRouter>
      <NavigationBar/>
      <Routes>
        <Route exact path='/' element={<HomePage/>} />
        <Route exact path='/*' element={<DoesNotExistPage/>} />
      </Routes>

  </BrowserRouter>

 );
 
}

export default App;
