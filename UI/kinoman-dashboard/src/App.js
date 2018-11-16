import React from 'react';
import { Container } from 'semantic-ui-react';
import { BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import TopMenu from './Pages/Components/Menu';
import List from './Pages/List';
import NewProduct from './Pages/NewProduct';
// import NewProduct from './Pages/TestTagInput';



class App extends React.Component {
	render() {
		return (
			<div>
				<Router>
					<div>
						<TopMenu />
						<Container>
							<Switch>
								<Route exact path='/' component={ List } />
								<Route path='/new-movie' component={ NewProduct } />
							</Switch>
						</Container>
					</div>
				</Router>
			</div>
		)
	}
}

export default App;