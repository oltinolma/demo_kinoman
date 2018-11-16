import React from 'react';
import { Link } from 'react-router-dom';
import { Menu, Container, Button } from 'semantic-ui-react';

class TopMenu extends React.Component {

  render(){
    return (
      <Menu inverted>
        <Container>
          <Link to='/list'>
            <Menu.Item>
              <Button >List</Button>
            </Menu.Item>
          </Link>
          <Link to='/new-product'>
            <Menu.Item>
              <Button >New product</Button>
            </Menu.Item>
          </Link>
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
