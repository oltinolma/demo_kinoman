import React from 'react';
import TagInpInput from './TagInput';
import { Form } from 'semantic-ui-react';

const TaxonomyField = (props) => {
    return (
        <Form.Field required>
            <label>{props.parent.toUpperCase()}</label>
            <TagInpInput tagTaxonomy={props.parent} tagId={props.tagId} sendList={ props.sendList } />
        </Form.Field>
    )
}

class Taxanomy extends React.Component {
    constructor (props){
        super(props);
        this.state = {};
        this.setState = this.props.obj.taxonomy;
    }
    render (props){
        const taxList = this.props.obj;
        return (
            <Form>
                {taxList.map(item => 
                    <TaxonomyField 
                        key={item.id}
                        tagId={item.id}
                        parent={item.name} 
                        taxonomy={item.value} 
                        sendList={this.props.sendList} 
                    />    
                )}
            </Form>
        )
    }
}

export default Taxanomy;