import React from 'react';
import Axios from 'axios';
import uuidv4 from 'uuid/v4';
import { Grid, Button, Container } from 'semantic-ui-react';
import StandardForm from './Components/StandardForm';
import Taxanomy from './Components/Taxanomy';

class NewProduct extends React.Component {
    constructor (props) {
        super(props);

        this.state = {taxonomy: []};

        this.listOfTaxonomies = [];

        this.readyObject = {
            "payload": {
                "movie": {},
                "file": "",
                "taxonomy": []
            }
        };

        this.sortedTaxonomies = [];
        
        this.listOfStandards = {
            "id": null,
            "name": null,
            "releasedDate": null,
            "fullName": null,
            "ageRating": null,
            "description": null
        }
        
        this.newTaxonomyList = [];
    }
    
    componentDidMount() {
        Axios({
            method : 'POST',
            url: 'http://rest1.oltinolma.uz:7576/v1/request',
            headers: {
                'X-Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbiI6ImFkbWluIiwiaXNzIjoiaHR0cDovL2tpbm9tYW4ub2x0aW5vbG1hLnV6IiwiaWF0IjoxNTQxNzM1NjM3LCJleHAiOjE1NDI2MzU2Mzd9._kcUclcdj9o-HgqrTIh-ykzAwKUGJUkdS_dMjdk93uudGWayg3DMNrznS55lIEmpktRuqZydP6EKYoHY6hGVGA',
                'Routing-key': 'taxonomy.menu.list'
            }
        }).then(res => {
            if(res.status === 200) {
                this.setState({ taxonomy: [...res.data]});
                this.listOfTaxonomies = this.state.taxonomy;
            }
        });
    }

    getStandardInfo = (name, value) => {
        for(var i in this.listOfStandards) {
            if(i === name){
                this.listOfStandards[i] = value;
            }
        }
    }

    getNewTaxonomies = (taxonomy, list) => {
        // console.log(taxonomy, list)
        this.listOfTaxonomies.forEach(key => {
            if(key.name === taxonomy) {
                key.value = list;
            }
        })
        // console.log(this.listOfTaxonomies)
    }

    orderTaxonomy = (obj) => {
        if(obj) {
            obj.forEach(element => {
                if(element.value && element.value.length === 1) {
                    let holder = new Object();
                    holder.parent = element.name;
                    holder.value = element.value[0];
                    holder.status = 'inserted';
                    this.sortedTaxonomies.push(holder);
                } else if(element.value && element.value.length > 1) {
                    element.value.forEach(item => {
                        let holder = new Object();
                        holder.parent = element.name;
                        holder.value = item;
                        holder.status = 'inserted';
                        this.sortedTaxonomies.push(holder);
                    })
                }
            });
        }
    }

    addNew = () => {
        const uuid = uuidv4();
        this.orderTaxonomy(this.listOfTaxonomies);
        this.readyObject.payload.movie = this.listOfStandards;
        this.readyObject.payload.file = this.listOfStandards.id;
        this.readyObject.payload.taxonomy = this.sortedTaxonomies;
        this.readyObject.payload.movie.id = uuid;
        console.log(this.readyObject);
    }

    addNewMovie = (obj) => {
        Axios({
            method: 'POST',
            url: 'http://rest1.oltinolma.uz:7576/v1/send',
            data: obj,
            headers: {
                'X-Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbiI6ImFkbWluIiwiaXNzIjoiaHR0cDovL2tpbm9tYW4ub2x0aW5vbG1hLnV6IiwiaWF0IjoxNTQxODI0ODUxLCJleHAiOjE1NDI3MjQ4NTF9.V9JV9MMi1ATca5GBSeVDGNgemt9zZY803k2RM7PLRjJnZPlk8-ePPwtdSdLu4P4goW2zjiqNka8fbWFVuSbsUw',
                'Routing-Key': 'movie.insert.or.update'
            }
        }).then(res => console.log(res))
    }

    render (){
        return (
            <Grid columns={3} stackable>
                <Grid.Row>
                    
                    <Grid.Column>
                        <StandardForm sendList={this.getStandardInfo} formSubmit={this.addNew} />
                    </Grid.Column>

                    <Grid.Column>
                        <Taxanomy obj={this.state.taxonomy} sendList={this.getNewTaxonomies} />
                    </Grid.Column>

                </Grid.Row>
                <Container>
                    {/* <Button onClick={this.addNew}>Add new movie</Button> */}
                </Container>
            </Grid>
        )
    }
}

export default NewProduct;