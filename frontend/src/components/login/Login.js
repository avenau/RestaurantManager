import React, { useState } from 'react';
import axios from 'axios';
import { setAccountSession } from '../../utils/Auth';
import { BACKEND_LOGIN_URI, UNAUTHORIZED } from '../../utils/Const';


import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

//redux crab
import { useDispatch } from 'react-redux';
import { updateAuth } from '../../actions';

// utils
import Notification from '../notification/Notification';

import { updateAccountType, updateLength } from "../../redux/toolbar";
import { Container } from 'react-bootstrap';



function Login(props) 
{
    const [error, setError] = useState(null);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    function validateForm() {
        return username.length > 0 && password.length > 0;
    }

    // React redux stuff
    const dispatch = useDispatch();

    /*
       const axiosConfig = {
            headers: { Authorization: `Bearer ${sessionStorage.token}`}
        };
        .post(/getPqages, { page:id }, axiosConfig)
        .catch()
    */

    // handle button click of login form
    const handleLogin = (event) => 
    {
        event.preventDefault();
        setError(null);

        axios
        .post(BACKEND_LOGIN_URI, 
            { username: username, password: password }, 
            {
            withCredentials: true,
            crossDomain: true,
            headers: {
              'Content-Type': 'application/json',
              'Access-Control-Allow-Origin': '*'
            }}
            )
        .then(response => {
            // Login failure : data.statusCode 
            if (response.data.statusCode === UNAUTHORIZED)
            {
                setError(response.msg);
                Notification({  type: 'warning',
                                msg: response.data.msg,
                                title: 'Login Failure'
                            });
                return;
            }
            console.log("response data" + response.data.userId);
            console.log("response " + response);
            setAccountSession(response.data.jwtToken, response.data.username, response.data.accountType, response.data.userId);
            dispatch(updateAccountType());
            dispatch(updateLength());

            dispatch(updateAuth());
            
            Notification({  type: 'success',
                            msg: response.msg,
                            title: 'Login Success'
                        });
            props.history.push('/');
        })
        // = internal server error
        .catch(error => {
            console.log(error);
            if (error.response.status === 401) {
                setError(error.response.message);
            }
            else setError("Something in the backend went wrong. Please try again later.");

            Notification({  type: 'error',
                            msg: 'internal server error ggwp',
                            title: 'Server Error'
            })
        });
    }

    return (

        <Form onSubmit={handleLogin}>
            {error && <><small style={{ color: 'red' }}>{error}</small><br /></>}
            <Form.Group size="lg" controlId="username">
            <Form.Control
                autoFocus
                className="mb-3"
                type="username"
                value={username}
                placeholder="Username"
                onChange={(e) => setUsername(e.target.value)}
            />
            </Form.Group>
            <Form.Group size="lg" controlId="password">
            <Form.Control
                className="mb-3"
                type="password"
                value={password}
                placeholder="Password"
                onChange={(e) => setPassword(e.target.value)}
            />
            </Form.Group>
            <div className="d-grid gap-2">
                <Button block type="submit" disabled={!validateForm()}>
                Login
                </Button>
            </div>
        </Form>

    );
}

export default Login;