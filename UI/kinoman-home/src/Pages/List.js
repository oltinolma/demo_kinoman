import React from 'react';
import { Link } from 'react-router-dom';
import { Grid } from 'semantic-ui-react';
import MovieCard from './Components/Card';
import './Style.css';

class List extends React.Component {
  constructor(props) {
    super(props);
    this.some = true;
    this.state = {list: []};
  }

  renderContent = (show, list) => {
    if(list.length > 0 ) {
      return (
        <Grid columns={4} doubling>
          <Grid.Row>
              {list.map((item => 
                <Grid.Column key={item.movie.id}>
                <Link to=''>
                  <MovieCard obj={item} />
                </Link>
                </Grid.Column>
              ))}
          </Grid.Row>
        </Grid>
      );
    } 
    if(list.length < 1 && show === true) {
      return (
        <p className='nothing-found'>Nothing found <br /><span>Please search another movie</span></p>
      )
    }
  }
  
  render(){

    let list = [];
    this.props.showSearchResult ? list = this.props.searchResult : list = this.props.latesMovies;
    
    return (
      <div>
        {this.renderContent(this.props.showSearchResult, list)}
      </div>
    )
  }
}

export default List;