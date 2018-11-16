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
            url: 'http://rest1.oltinolma.uz:7576/uploadFile',
			process: {
                headers: {
                    'X-Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbiI6ImFkbWluIiwiaXNzIjoiaHR0cDovL2tpbm9tYW4ub2x0aW5vbG1hLnV6IiwiaWF0IjoxNTQxNzYwMzgzLCJleHAiOjE1NDI2NjAzODN9.0m63za5WyUp0sBbtxlACzws4b8gzjdKZ1sNhUZsz_4KE7wzq0-E8ux6Ft2KEV91MjNjMY9laKg1D0UYAtRwfTQ',
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