import { Navbar, Nav, NavDropdown, Container, NavLink, Button, Modal, Form, Tab, Tabs } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { removeUserSession, setAccountSession } from '../../utils/Auth';
import { MdNotifications } from "react-icons/md";
import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { updateAccountType, updateLength } from "../../redux/toolbar";
import Login from "../login/Login";
import './NavBar.css';


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
        const [show, setShow] = useState(false);

        const handleClose = () => setShow(false);
        const handleShow = () => setShow(true);
        const [key, setKey] = useState('login');
        const login = (() => {

        })

        return (
            <>
                <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
                    <Container>
                    <Navbar.Brand onClick={() => {history('/')} }>Maccas</Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="me-auto">

                        </Nav>
                        <Nav>
                            <NavLink  onClick={handleShow} activeClassName='active'>
                                Login
                            </NavLink>
                        </Nav>
                    </Navbar.Collapse>
                    </Container>
                </Navbar>

                <Modal show={show} onHide={handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title className="mx-auto" style={{ position: 'relative', left: '30px' }}>
                            Please Login To Continue
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Tabs
                            id="controlled-tab-example"
                            activeKey={key}
                            onSelect={(k) => setKey(k)}
                            className="mb-3"
                            >
                            <Tab eventKey="login" title="Login">
                                <Login/>
                            </Tab>
                            <Tab eventKey="signup" title="Sign Up">
                            <Form>
                                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                    <Form.Control
                                        type="text"
                                        placeholder="Username"
                                        autoFocus
                                    />
                                    </Form.Group>
                                    <Form.Group
                                    className="mb-3"
                                    controlId="exampleForm.ControlTextarea1"
                                    >
                                    <Form.Control 
                                        type="password" 
                                        placeholder="Password"
                                    />                                  
                                    </Form.Group>
                                    <Form.Group
                                    className="mb-3"
                                    controlId="exampleForm.ControlTextarea1"
                                    >
                                    <Form.Control 
                                        type="password" 
                                        placeholder="Confirm Password"
                                    />                                  
                                    </Form.Group>
                                    <div className="d-grid gap-2">
                                        <Button variant="primary" onClick={handleClose}>
                                            Sign Up
                                        </Button>
                                    </div>
                                </Form>
                            </Tab>
                        </Tabs>
                    </Modal.Body>
                    <Modal.Footer>
                        
                    </Modal.Footer>
                </Modal>
            </>

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
