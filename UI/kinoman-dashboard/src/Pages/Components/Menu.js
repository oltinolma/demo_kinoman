import React from 'react';
import { Link } from 'react-router-dom';
import { Menu, Container, Button } from 'semantic-ui-react';
import '../../App.css';

class TopMenu extends React.Component {

  render(){
    return (
      <Menu inverted className='dashboard-navbar'>
        <Container>
          <Menu.Item link as={ Link } to='/'>
            List
          </Menu.Item>
          <Menu.Item link as={ Link } to='/new-movie'>
            New Movie
          </Menu.Item>
          <Menu.Menu position='right'>
            <Link to=''>
              <Menu.Item>
                <Button>
                  Log out
                </Button>
              </Menu.Item>
            </Link>
          </Menu.Menu>
        </Container>
      </Menu>
    )
  }
}
export default TopMenu;
