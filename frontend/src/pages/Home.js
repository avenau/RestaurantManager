import { useState } from "react";
import { useNavigate } from "react-router-dom";
//import BrowseQuiz from "../components/quiz/BrowseQuiz"
//import QuizStartPage from "../components/quiz/BrowseQuiz"
import { Button, Container, Row, Card, Image, Carousel } from 'react-bootstrap';

function HomePage() {
    let history = useNavigate();

    //To Redirect Page
    /*
        history.push('route') -> This will redirect you to the page
        route being the path that was set in App.js
    */

    let nav = <LoggedOut/>;
    

    function LoggedOut () {

        return (                      
            <Carousel variant="dark">
                <Carousel.Item>
                    <img
                    className="d-block w-100"
                    src="https://s7d1.scene7.com/is/image/mcdonalds/mcdonalds-McSpicy-New:1-3-product-tile-desktop?wid=829&hei=515&dpr=off"
                    alt="First slide"
                    />
                    <Carousel.Caption>
                    <h3>First slide label</h3>
                    <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item>
                    <img
                    className="d-block w-100"
                    src="https://mcdonalds.com.au/sites/mcdonalds.com.au/files/MicrosoftTeams-image%20%2861%29.png"
                    alt="Second slide"
                    />
                    <Carousel.Caption>
                    <h3>Second slide label</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item>
                    <img
                    className="d-block w-100"
                    src="https://www.highpoint.com.au/Upload/Highpoint/Media/Store-Logo/mcdonalds-logo.png?width=250&height=161"
                    alt="Third slide"
                    />
                    <Carousel.Caption>
                    <h3>Third slide label</h3>
                    <p>
                        Praesent commodo cursus magna, vel scelerisque nisl consectetur.
                    </p>
                    </Carousel.Caption>
                </Carousel.Item>
            </Carousel>
        );
    }

    
    function LoggedIn () {

        return (                      
            <Card className="text-center">
                <Card.Header>SkillUp</Card.Header>
                <Card.Body>
                    <Card.Title>Welcome back {sessionStorage.getItem('username')}</Card.Title>
                    <Card.Text>
                        
                    </Card.Text>
                </Card.Body>
                <Card.Footer className="text-muted"></Card.Footer>
            </Card>

        )
    }

    if (sessionStorage.length == 0){
        nav = <LoggedOut/>
    } else {
        nav = <LoggedIn/>
    }



    return (
        <Container className="pt-4">
            {nav}
        </Container>
    )
}
export default HomePage;


// <Container>
// <h1>HOME</h1>
// <Row className="mt-4">
//     <Button variant="secondary" onClick={() => { history.push('/profile') }}>My Profile</Button>
// </Row>
// <Row className="mt-4">
//     <Button variant="secondary" onClick={() => { history.push('/startquiz') }}>Start Skill Quiz</Button>
// </Row>
// <Row className="mt-4">
//     <Button variant="secondary" onClick={() => { history.push('/placements') }}>Search For Placements</Button>
// </Row>
// <Row className="mt-4">
//     <Button variant="secondary" onClick={() => { history.push('/createPlacement') }}>Create Placement</Button>
// </Row>
// <Row className="mt-4">
//     <Button variant="secondary" onClick={() => { history.push('/suggestskill') }}>Suggest Skill</Button>
// </Row>
// <Row className="mt-4">
//     <Button variant="secondary" onClick={() => { history.push('/browsequiz') }}>Browse Quiz</Button>
// </Row>
// <Row className="mt-4">
//     <Button variant="secondary" onClick={() => { history.push('/myskills') }}>Trainee: My Skills Page</Button>
// </Row>
// <Row className="mt-4">
//     <Button variant="secondary" onClick={() => { history.push('/traineeResults') }}>Trainee Results</Button>
// </Row>
// <Row className="mt-4">
//     <Button variant="secondary" onClick={() => { history.push('/searchtrainee') }}>Search For Trainees</Button>
// </Row>
// </Container>