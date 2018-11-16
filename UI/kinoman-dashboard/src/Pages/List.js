import React from 'react';
import { Grid } from 'semantic-ui-react';
import MovieCard from './Components/Card';
// import DeleteModal from './Components/Modal';

class List extends React.Component {
  constructor(props) {
    super(props);

    this.deleteItem = this.deleteItem.bind(this);
    
    this.state = {list: [
      {
        id: 1,
        img: 'https://imgc.allpostersimages.com/img/print/u-g-F6D1OA0.jpg?w=550&h=550&p=0',
        name: 'John Doe',
        date: '10.09.2018',
        description: 'Matthew is a musician living in Nashville.',
        watched: '9822'
      },
      {
        id: 2,
        img: 'https://imgc.allpostersimages.com/img/print/u-g-F4JAYE0.jpg?w=550&h=550&p=0',
        name: 'John Doe',
        date: '10.09.2018',
        description: 'Matthew is a musician living in Nashville.',
        watched: '9822'
      },
      {
        id: 3,
        img: 'https://imgc.allpostersimages.com/img/print/u-g-F4TCGS0.jpg?w=550&h=550&p=0',
        name: 'John Doe',
        date: '10.09.2018',
        description: 'Matthew is a musician living in Nashville.',
        watched: '9822'
      },
      {
        id: 4,
        img: 'https://imgc.allpostersimages.com/img/print/u-g-F991KX0.jpg?w=550&h=550&p=0',
        name: 'John Doe',
        date: '10.09.2018',
        description: 'Matthew is a musician living in Nashville.',
        watched: '9822'
      },
      {
        id: 5,
        img: 'https://imgc.allpostersimages.com/img/print/u-g-F98UE80.jpg?w=550&h=550&p=0',
        name: 'John Doe',
        date: '10.09.2018',
        description: 'Matthew is a musician living in Nashville.',
        watched: '9822'
      }
    ]}
  }
  deleteItem(e){
  }
  render(){
    const list = this.state.list;
    return (
      <div>
        <Grid columns={5} doubling>
          <Grid.Row>
            {list.map((item => 
              <Grid.Column key={item.id}>
                <MovieCard delete={this.deleteItem} obj={item} />
              </Grid.Column>
            ))}
          </Grid.Row>
        </Grid>
      </div>
    )
  }
}

export default List;