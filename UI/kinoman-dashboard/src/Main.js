import React from "react";
import Axios from 'axios';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Link,
  Redirect,
  withRouter
} from "react-router-dom";

const AuthExample = () => (
  <Router>
    <div>
      <AuthButton />
      <ul>
        <li>
          <Link to="/public">Public Page</Link>
        </li>
        <li>
          <Link to="/img">Image uploader</Link>
        </li>
        <li>
          <Link to="/dashboard">Login</Link>
        </li>
      </ul>
        <Route path="/public" component={Public} />
        <Route path="/img" component={Image} />
        <Route path="/login" component={Login} />
        <PrivateRoute path="/dashboard" component={Dashboard} />
    </div>
  </Router>
);

const fakeAuth = {
  isAuthenticated: false,
  authenticate(cb) {
    this.isAuthenticated = true;
    setTimeout(cb, 100); // fake async
  },
  signout(cb) {
	localStorage.removeItem('user_access_token');
    this.isAuthenticated = false;
  }
};

const AuthButton = withRouter(
  ({ history }) =>
    fakeAuth.isAuthenticated ? (
      <p>
        Welcome!{" "}
        <button
          onClick={() => {
            fakeAuth.signout(() => history.push("/"));
          }}
        >
          Sign out
        </button>
      </p>
    ) : (
      <p>You are not logged in.</p>
    )
);

const PrivateRoute = ({ component: Component, ...rest }) => (
  <Route
    {...rest}
    render={props =>
      fakeAuth.isAuthenticated ? (
        <Dashboard {...props} />
      ) : (
        <Redirect
          to={{
            pathname: "/login",
            state: { from: props.location }
          }}
        />
      )
    }
  />
);

const Public = () => <h3>Public</h3>;
const Dashboard = () => <h3>Dashboard page</h3>;
const NotFound = () => <h3>404 page not found</h3>;

class Login extends React.Component {
  state = {
    redirectToReferrer: false
  };

  login = () => {
      fakeAuth.authenticate(() => {
        this.setState({ redirectToReferrer: true });
      });
  };

  render() {
    const { from } = this.props.location.state || { from: { pathname: "/" } };
    const { redirectToReferrer } = this.state;

    if (redirectToReferrer) {
      return <Redirect to={from} />;
    }

    return (
      <div>
        <p>You must log in to view the page at {from.pathname}</p>
        <input type='text' ref='login' placeholder='login' />
        <input type='text' ref='password' placeholder='password' />
        <button onClick={this.login}>Log in</button>
      </div>
    );
  }
}

export default AuthExample;