import React from 'react';
import '../HomeComponents/style.css';
import TextTruncate from 'react-text-truncate';
import { Card, Image, Icon, Button } from 'semantic-ui-react'

const MovieCard = (props) =>{
    return(
      <Card className='movieCard'>
        <Image src={props.obj.file} />
        <Card.Content>
          <Card.Header>{props.obj.movie.name}</Card.Header>
          <Card.Meta>
            <span className='date'>{props.obj.movie.releasedDate}</span>
          </Card.Meta>
          <Card.Description>
          <TextTruncate line={3} truncateText="…" text={props.obj.movie.description} />
          </Card.Description>
        </Card.Content>
      </Card>
    )
  }

export default MovieCard;