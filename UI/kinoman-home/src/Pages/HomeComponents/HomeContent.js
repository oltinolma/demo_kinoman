import React from 'react';
import { Switch, Route } from 'react-router-dom';
import { Menu, Input, Dropdown, Icon, Segment } from 'semantic-ui-react';
import List from '../List';

const LeftMenu = () => {
  return(
    <Dropdown item icon='th large' simple>
          <Dropdown.Menu>
            <Dropdown.Item>Open</Dropdown.Item>
            <Dropdown.Item>Save...</Dropdown.Item>
            <Dropdown.Item>Edit Permissions</Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Header>Export</Dropdown.Header>
            <Dropdown.Item>Share</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
  )
}

const LatesMovies = (props) => {
  console.log(props.latesMovies)
  return (
    <div>
      {props.latesMovies}
    </div>
  )
}

export default class HomeContent extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <div>
        <Menu attached='top'>

          <LeftMenu />
    
          <Menu.Menu position='right'>
            <div className='ui right aligned category search item'>
              <div className='ui transparent icon input'>
                <form onSubmit={this.props.globalSearch}>
                    <input className='no-border' ref={this.props.globalQuery} type='text' placeholder='Search movie' autoFocus />
                    <i onClick={this.props.globalSearch} className='search link icon' />
                </form>
              </div>
              <div className='results' />
            </div>
          </Menu.Menu>

        </Menu>
        <Segment attached='bottom'>
          <List searchResult={this.props.searchResult} latesMovies={this.props.latesMovies} showSearchResult={this.props.showSearchResult} />
          <Switch>
            {/* <Route exact path='/' component={List}/> */}
            {/* <Route path='/movies/:id' component={SingleMovie}/> */}
          </Switch>
        </Segment>
      </div>
    )
  }
}
