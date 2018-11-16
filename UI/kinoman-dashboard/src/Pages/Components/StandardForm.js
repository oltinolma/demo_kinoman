import React from 'react';
import { Form, Button } from 'semantic-ui-react';
import { DateInput } from 'semantic-ui-calendar-react';
import MoviePoster from './MoviePoster';

class ReleaseDate extends React.Component {
    constructor(props) {
      super(props);
   
      this.state = {
        date: '',
        name: ''
      };
    }
   
    handleChange = (event, {name, value}) => {
        this.setState({ date: value, name: name });
    }

    componentDidUpdate() {
        this.props.sendList(this.state.name, this.state.date)
    }
   
    render() {
      return (
        <DateInput
            required
            dateFormat='YYYY-MM-DD'
            name="releasedDate"
            placeholder="Date"
            value={this.state.date}
            iconPosition="left"
            onChange={this.handleChange} />
      );
    }
  }

class StandardForm extends React.Component {

    constructor() {
        super();
        this.standardFormList = {};
    }

    changeEventHandler = (e) => {
        this.props.sendList(e.target.name, e.target.value);
    }

    render (){
        return (
            <Form onSubmit={this.props.formSubmit}>
                <Form.Field>
                    <label>POSTER IMAGE</label>
                    <MoviePoster sendImgId={this.props.sendList} />
                </Form.Field>
                <Form.Field>
                    <label>NAME</label>
                </Form.Field>
                    <input placeholder='Name' name='name' onChange={this.changeEventHandler} required />
                <Form.Field>
                    <label>FULL NAME</label>
                    <input placeholder='Full Name' name='fullName' onChange={this.changeEventHandler} required />
                </Form.Field>
                <Form.Field>
                    <label>RELEASE DATE</label>
                    <ReleaseDate sendList={this.props.sendList} />
                </Form.Field>
                <Form.Field>
                    <label>AGE</label>
                    <input placeholder='Age rating' name='ageRating' onChange={this.changeEventHandler} required />
                </Form.Field>
                <Form.TextArea label='DESCRIPTION' name='description' onChange={this.changeEventHandler} required placeholder='Give some description' />
                <Form.Field>
                    <Button type='submit'>Add new movie</Button>
                </Form.Field>
            </Form>
        )
    }
}

export default StandardForm;