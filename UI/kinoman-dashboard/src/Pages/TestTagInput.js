import _ from 'lodash'
import faker from 'faker'
import React, { Component } from 'react'
import { Button, Dropdown, Grid, Header } from 'semantic-ui-react'

const getOptions = () =>
  _.times(3, () => {
    const name = faker.name.findName()
    return { key: name, text: name, value: _.snakeCase(name) }
  })

class NewProduct extends Component {
  componentWillMount() {
    this.setState({
      isFetching: false,
      multiple: true,
      search: true,
      searchQuery: null,
      value: [],
      options: getOptions(),
    })
  }
  handleAddition = (e, { value }) => {
    this.setState({
      options: [{ text: value, value }, ...this.state.options],
    })
  }
  handleChange = (e, { value }) => this.setState({ value })
  handleSearchChange = (e, { searchQuery }) => this.setState({ searchQuery })

  fetchOptions = () => {
    this.setState({ isFetching: true })

    setTimeout(() => {
      this.setState({ isFetching: false, options: getOptions() })
    }, 500)
  }

  render() {
    const { multiple, options, isFetching, search, value } = this.state

    return (
      <Grid>
        <Grid.Column width={8}>
          <p>
            <Button onClick={this.fetchOptions}>Fetch</Button>
          </p>
          <Dropdown
            allowAdditions
            onAddItem={this.handleAddition}
            fluid
            selection
            multiple={multiple}
            search={search}
            options={options}
            value={value}
            placeholder='Add Users'
            onChange={this.handleChange}
            onSearchChange={this.handleSearchChange}
            disabled={isFetching}
            loading={isFetching}
          />
        </Grid.Column>
        <Grid.Column width={8}>
          <Header>State</Header>
          <pre>{JSON.stringify(this.state, null, 2)}</pre>
        </Grid.Column>
      </Grid>
    )
  }
}

export default NewProduct