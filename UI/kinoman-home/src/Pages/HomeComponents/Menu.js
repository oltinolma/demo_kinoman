import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import { Menu, Dropdown } from 'semantic-ui-react';

export default class TopMenu extends Component {
  constructor(props){
    super(props);
  }
  state = {}

  handleItemClick = (e, { name, taxonomy }) => {
    this.setState({ activeItem: name });
    // alert(taxonomy);
    this.props.getMenu(taxonomy)
  } 

  render() {

    const { activeItem } = this.state;

    return (
      <Menu>
        <Menu.Item onClick={this.props.removeSearchResult} ><b>Kinoman</b></Menu.Item>
        <Menu.Item name='serials' taxonomy='series' active={activeItem === 'serials'} onClick={this.handleItemClick}>
          Serials
        </Menu.Item>
        <Dropdown text='Heavy genres' pointing className='link item'>
          <Dropdown.Menu>
            <Dropdown.Item onClick={this.handleItemClick} taxonomy='horror'>Horror</Dropdown.Item>
            <Dropdown.Item onClick={this.handleItemClick} taxonomy='crime'>Crime</Dropdown.Item>
            <Dropdown.Item onClick={this.handleItemClick} taxonomy='mystery'>Mystery</Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item onClick={this.handleItemClick} taxonomy='history'>History</Dropdown.Item>
            <Dropdown.Item onClick={this.handleItemClick} taxonomy='drama'>Drama</Dropdown.Item>
            <Dropdown.Item onClick={this.handleItemClick} taxonomy='documentaries'>Documentaries</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
        <Dropdown text='General genres' pointing className='link item'>
          <Dropdown.Menu>
            <Dropdown.Item>
              <Dropdown text='Comedy'>
                <Dropdown.Menu>
                  <Dropdown.Item onClick={this.handleItemClick} taxonomy='black humor'>Black humor</Dropdown.Item>
                  <Dropdown.Item onClick={this.handleItemClick} taxonomy='romantic comedy'>Romantic Comedy</Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            </Dropdown.Item>
            <Dropdown.Item>
              <Dropdown text='Animated'>
                <Dropdown.Menu>
                  <Dropdown.Item onClick={this.handleItemClick} taxonomy='3d'>3D</Dropdown.Item>
                  <Dropdown.Item onClick={this.handleItemClick} taxonomy='cartoons'>Cartoons</Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            </Dropdown.Item>
            <Dropdown.Item onClick={this.handleItemClick} taxonomy='adventure'>Advanture</Dropdown.Item>
            <Dropdown.Item onClick={this.handleItemClick} taxonomy='family'>Family</Dropdown.Item>
            <Dropdown.Item onClick={this.handleItemClick} taxonomy='biography'>Biography</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
        <Menu.Item as={ Link } position='right' to='login' link>Login</Menu.Item>
      </Menu>
    )
  }
}
