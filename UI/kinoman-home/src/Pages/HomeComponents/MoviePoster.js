import React from 'react';
import {FilePond, File, registerPlugin  } from 'react-filepond';

import 'filepond/dist/filepond.min.css';
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import FilePondPluginImagePreview from 'filepond-plugin-image-preview';
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css';
registerPlugin( FilePondPluginImagePreview, FilePondPluginFileValidateType );

class MoviePoster extends React.Component {
    constructor(props) {
        super(props);

		this.state = {
            files: ['']
        };
        this.imgId = {}
        this.getImgRes = this.getImgRes.bind(this);
    }
    
    getImgRes(error, file) {
        const res = JSON.parse(file.serverId);
        this.props.sendImgId('id', res.file.id);
    }
    
    componentDidUpdate() {
    }

    render(){
        const serverConfig = {
            url: 'http://192.168.0.127:7576/uploadFile',
			process: {
                headers: {
                    'X-Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6WyJhZG1pbmlzdHJhdG9yIl0sImlkX3VzZXIiOiI3MTc0YzlhNi0zNzAyLTQ4MzItODllZi1jMWQ4ZDM5NjIzZTIiLCJpc3MiOiJodHRwOi8va2lub21hbi5vbHRpbm9sbWEudXoiLCJpYXQiOjE1Mzk5NjUzNzcsImV4cCI6MTU0MDg2NTM3N30.1YFfgrQKZkx48SqJlF-ni3X0gh8fCIjBvCTsvWxOI4PGhbEKGpMEop87PxEHV4rtnSZCepObG6C_3EdTjn5OFA',
					'Routing-Key':'file.upload'
                },
                onload: function(res) {
                    return res;
                }
			}
		};
		return (
			<div className="App">
            
            <FilePond ref={ref => this.pond = ref}
                name='file'
                required={true}
                allowImagePreview={true}
                allowMultiple={false}
                maxFiles={1}
                labelFileTypeNotAllowed='Available formats: jpg, jpeg, png'
                allowFileTypeValidation={true}
                acceptedFileTypes={['image/png', 'image/jpeg', 'image/jpg']}
                onprocessfile={this.getImgRes}
                server={serverConfig}
                >
            
                {this.state.files.map(file => (
                    <File key={file} src={file}  />
                ))}
                
            </FilePond>
      </div>
		)
	}
}

export default MoviePoster;