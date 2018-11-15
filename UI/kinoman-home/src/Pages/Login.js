import React from "react";
import './Style.css';
import { Button, Form, Grid } from 'semantic-ui-react';
import Axios from 'axios';

import {
  BrowserRouter as Router,
  Link,
} from "react-router-dom";


export default class Login extends React.Component {
  constructor() {
    super();
    this.state = {
      login: null,
      password: null
    }
  }
  changeEventHandler = (e) => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }
  authLogin = () => {
    const headers = {
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'
    }
    Axios.post('http://192.168.0.50:7576/auth/login', 
      { login: this.state.login, password: this.state.password },
      { headers: headers}
    )
    .then(res => {
      if(res.status === 200 ) {
        localStorage.setItem('ku_token', res.data.token);
      }
      console.log(res)
    })
    .catch(err => console.log(err))
  }
  render() {
    return (
      <div>
        <Grid className='loginBox' columns={3} stackable  verticalAlign='middle'>
          <Grid.Row centered>
            <Grid.Column>
              <h1 className='loginHeading'>Kinoman</h1>
              <Form onSubmit={this.authLogin}>
                <Form.Field>
                  <label>Login</label>
                  <input placeholder='Login' name='login' onChange={this.changeEventHandler} ref={this.login} required />
                </Form.Field>
                <Form.Field>
                  <label>Password</label>
                  <input type='password' name='password' onChange={this.changeEventHandler} placeholder='Password' ref={this.password} required />
                </Form.Field>
                <Button type='submit'>Submit</Button>
              </Form>
              <div className='backLink'>
                <Link to='/'>Back to home</Link>
              </div>
            </Grid.Column>
          </Grid.Row>
        </Grid>
      </div>
    )
  }
}