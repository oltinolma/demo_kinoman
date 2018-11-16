import React, { Component } from 'react';
import axios from 'axios';
import { Dropdown } from 'semantic-ui-react';

let options = [
  { text: '0', value: '0' },
  { text: '10', value: '10' },
  { text: '12+', value: '12+' },
  { text: '16+', value: '16+' },
  { text: '18+', value: '18+' },
]

class TagInput extends Component {
  constructor(props){
    super(props); 
  }
  state = { options }
  
  handleAddition = (e, { value }) => {
    this.setState({
      options: [{ text: value, value }, ...this.state.options],
    })
  }

  handleChange = (e, { value, name }) => {
    this.setState({ currentValues: value, name })
  } 
  
  elasticSearch = (e, { searchQuery, name }) => {
    if(searchQuery.length > 2) {
      console.log('hello');
      // const headers = {
      //   'X-Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6WyJhZG1pbmlzdHJhdG9yIl0sImlkX3VzZXIiOiI3MTc0YzlhNi0zNzAyLTQ4MzItODllZi1jMWQ4ZDM5NjIzZTIiLCJpc3MiOiJodHRwOi8va2lub21hbi5vbHRpbm9sbWEudXoiLCJpYXQiOjE1Mzk5ODQwMjgsImV4cCI6MTU0MDg4NDAyOH0.5O0zJM8b8_LoiyWGyRo0C6uXqf3iSZJHbY8-AwH8sHSuxEODykiqZwC_fAcyWm3RxTWlahld97VGlTGGTwitkA',
      //   'Routing-Key': 'taxonomy.index'
      // }
      // axios.get(`http://192.168.0.50:7576/temp/search/for/taxonomy?parent=${name}&term=${searchQuery}`, {headers: headers})
      // .then(res => {
      //   // this.setState({ options: [{currentValues: searchQuery, name: name }]})
      // })
      // .catch(err => console.log(err));
    }
  }
  
  
  componentDidUpdate(){
    this.props.sendList(this.state);
  }
  
  
  render() {
    const { currentValues } = this.state;
    
    return (
        <Dropdown
            required
            options={this.state.options}
            placeholder={this.props.placeholder}
            name={this.props.placeholder}
            search
            selection
            fluid
            multiple
            allowAdditions
            value={currentValues}
            onAddItem={this.handleAddition}
            onChange={this.handleChange}
            onSearchChange={this.elasticSearch}
        />
    )
  }
}

export default TagInput;