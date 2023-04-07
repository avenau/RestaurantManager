import { Navbar, Nav, NavDropdown, Container, NavLink } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { removeUserSession, setAccountSession } from '../../utils/Auth';
import { MdNotifications } from "react-icons/md";
import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { updateAccountType, updateLength } from "../../redux/toolbar";
import './NavBar.css'


function NavigationBar() {
    const history = useNavigate();
    const accountType = useSelector((state) => state.toolbar.accountType);
    const sessionLength = useSelector((state) => state.toolbar.sessionLength);
    const dispatch = useDispatch();

    let nav = <LoggedOut/>

    useEffect (() => {
        // console.log("Login type: asdf " + accountType);
        // if (sessionStorage.length == 0){
        //     return(
        //         <LoggedOut/>
        //     )
        // } else if (sessionStorage.getItem("accountType") === "admin"){
        //     setType(sessionStorage.getItem("accountType"));
        //     console.log("Login type: admin " + accountType);
        //     return (
        //         <Admin/>
        //     )
        // } else if (sessionStorage.getItem("accountType") === "trainee"){
        //     setType(sessionStorage.getItem("accountType"));
        //     console.log("Login type: trainee " + accountType);
        //     return (
        //         <Trainee/>
        //     )
        // }
        console.log("RUNNING USEEFFECT");
    }, [accountType])

    function LogoutButton () {

        return (
            <NavLink  onClick={logoutFunction} activeClassName='active'>
                Log Out
            </NavLink>
        )
    }

    function ProfileButton () {
        return (
            <Nav.Link  onClick={() => {history('/profile')} }>{sessionStorage.getItem('username')}</Nav.Link>
        )
    }

    const logoutFunction = (() => {
        console.log("Logout type: " + sessionStorage.getItem("accountType"));
        removeUserSession();
        
        dispatch(updateAccountType());
        dispatch(updateLength());
        
        history('/');
    })

    function LoggedOut () {
        return (
            <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
                <Container>
                <Navbar.Brand onClick={() => {history('/')} }>SkillUp</Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">

                    </Nav>
                    <Nav>
                        <NavLink  onClick={() => {history('/login')} } activeClassName='active'>
                            Login
                        </NavLink>
                    </Nav>
                </Navbar.Collapse>
                </Container>
            </Navbar>
        )
    };


    if (sessionLength != 0 && accountType !== "admin" && accountType !== "trainee" && accountType !== "trainer" && accountType !== "sales"){
        dispatch(updateAccountType());
        dispatch(updateLength());
    }

    nav = <LoggedOut/>
        


    


    return (
        <div>{nav}</div>
        
    )
}
export default NavigationBar;
