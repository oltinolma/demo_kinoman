import React, { Component } from 'react';
import axios from 'axios';
import { Segment } from 'semantic-ui-react';
import '../style/style.css';
import { WithContext as ReactTags } from 'react-tag-input';

var list = [];

const KeyCodes = {
  comma: 188,
  enter: 13,
};

const delimiters = [KeyCodes.comma, KeyCodes.enter];

class TagInput extends Component {
  constructor(props){
    super(props);
    this.state = {
      tags: [],
      suggestions: []
    };
    this.handleDelete = this.handleDelete.bind(this);
    this.handleAddition = this.handleAddition.bind(this);
  }

  suggestionGenerator(list) {
    const suggestionList = [];
    list.map(e => {
      let suggestionId = null;
      suggestionId = e.id.toString()
      suggestionList.push({id: suggestionId, text: e.term});
    })
    this.setState({ suggestions: suggestionList})
    // console.log(suggestionList);
  }

  handleDelete(i) {
    const { tags } = this.state;
    this.setState({
      tags: tags.filter((tag, index) => index !== i),
    });
  }

  handleAddition(tag) {
   this.setState(state => ({ tags: [...state.tags, tag] }));
  }
  
  elasticSearch = (query) => {
    if(query.length >= 2 && query.length %2 === 0) {
      axios.get(`http://rest1.oltinolma.uz:7576/search/for/taxonomy?parent=${this.props.tagTaxonomy}&term=${query}`)
      .then(res => {
        if(res.data.length > 0) {
          // console.log(res.data)
          this.suggestionGenerator(res.data);
        }
      })
      .catch(err => console.log(err));
    }
  }

  componentDidUpdate(){
    const sortedTags = [...this.state.tags];
    const sortedArray = [];
    sortedTags.map(i => sortedArray.push(i.text));
    this.props.sendList(this.props.tagTaxonomy, sortedArray);
  }
  
  
  render() {
    const { tags, suggestions } = this.state;
    return (
      <Segment>
        <ReactTags
          minQueryLength={1}
          tags={tags}
          name='queryInput'
          taxonomy=''
          suggestions={suggestions}
          delimiters={delimiters}
          handleInputChange={this.elasticSearch}
          handleDelete={this.handleDelete}
          handleAddition={this.handleAddition}
        />
      </Segment>
    );
  }
}

export default TagInput;