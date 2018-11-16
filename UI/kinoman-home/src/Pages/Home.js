import React from 'react';
import axios from 'axios';
import TopMenu from './HomeComponents/Menu';
import HomeContent from './HomeComponents/HomeContent';
import { RSA_NO_PADDING } from 'constants';

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = { searchResult: [], latesMovies: [], show: false }
        this.globalQuery = React.createRef();
        this.latesMovies = [];
    }

    getMenu = (e) => {
        console.log('taxonomies', e)
        axios({
            method: 'POST',
            url: 'http://rest1.oltinolma.uz:7576/open/request',
            headers: {
                'Routing-Key' : 'movie.list.by.requested.taxonomies.for.menu'
            },
            data: {
                "params":{
                    "taxonomies":[e]
                }
            }
        }).then(res => {
            if(res.status === 200) {
                console.log(res)
                const list = res.data;
                this.setState({ searchResult: [...list], show: true })
            }
        })
    }
    
    removeSearchResult = () => {
        this.setState({ show: false });
    }

    componentDidMount() {
        axios({
            method: 'POST',
            url: 'http://rest1.oltinolma.uz:7576/open/request',
            headers: {
              'Routing-key': 'movie.list'
            }
        }).then(res => {
            if(res.status = 200) {
                this.latesMovies.push(res.data)
                this.setState({ latesMovies: [...res.data]})
            }
        })
    }

    globalSearch = (e) => {

        e.preventDefault();
        axios({
          method: 'POST',
          url: 'http://rest1.oltinolma.uz:7576/open/request',
          data: {
            params: { term: this.globalQuery.current.value }
          },
          headers: {
            'Routing-Key': 'global.search.movie.with.term'
          }
        }).then(res => {
          if(res.status == 200) {
            const list = res.data;
            this.setState({ searchResult: [...list], show: true })
          }
        })
        this.globalQuery.current.value = '';
    }

    render() {
        return (
            <div>
                <TopMenu removeSearchResult={this.removeSearchResult} getMenu={this.getMenu} />
                <HomeContent 
                    globalQuery={this.globalQuery}
                    globalSearch={this.globalSearch}
                    searchResult={this.state.searchResult}
                    showSearchResult={this.state.show}
                    latesMovies={this.state.latesMovies}
                />
            </div>
        )
    }
}

export default Home;