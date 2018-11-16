import React from 'react';
import { Container } from 'semantic-ui-react';
import { BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Login from './Pages/Login';
import Home from './Pages/Home';


class App extends React.Component {
	render() {
		return (
			<div>
				<Router>
					<div>
						<Container>
							<Switch>
								<Route exact path='/' component={Home} />
								<Route path='/login' component={Login} />
							</Switch>
						</Container>
					</div>
				</Router>
			</div>
		)
	}
}

export default App;