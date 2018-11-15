import React from 'react';
import { Card, Image, Icon, Button } from 'semantic-ui-react'

const MovieCard = (props) =>{
    return(
      <Card>
        <Image src={props.obj.img} />
        <Card.Content>
          <Card.Header>{props.obj.name}</Card.Header>
          <Card.Meta>
            <span className='date'>Joined in {props.obj.date}</span>
          </Card.Meta>
          <Card.Description>{props.obj.description}</Card.Description>
        </Card.Content>
        <Card.Content extra>
          <a>
            <Icon name='eye' />
            {props.obj.watched} Watched
          </a>
        </Card.Content>
        <Card.Content extra>
            <div className='ui two buttons'>
            <Button basic color='green'>
                Edit
            </Button>
            <Button basic color='red' onClick={props.delete}>
                Delete
            </Button>
            </div>
        </Card.Content>
      </Card>
    )
  }

export default MovieCard;