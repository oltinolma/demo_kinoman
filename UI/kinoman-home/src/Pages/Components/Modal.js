import React from 'react'
import { Button, Modal } from 'semantic-ui-react'

const DeleteModal = () => (
  <Modal
    trigger={<Button>Show Modal</Button>}
    header='Reminder!'
    content='Call Benjamin regarding the reports.'
    actions={['Cancel', { key: 'done', content: 'Delete', positive: true }]}
  />
)

export default DeleteModal;