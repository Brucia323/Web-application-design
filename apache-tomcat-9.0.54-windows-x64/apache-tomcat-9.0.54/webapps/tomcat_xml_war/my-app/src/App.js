import React from 'react'
import ReactDOM from 'react-dom'
import 'antd/dist/antd.css'
import './index.css'
import { Form, Input, Button, Checkbox, Layout, Menu } from 'antd'

import './App.css'
const { Header } = Layout

const Login = () => {
  const onFinish = (values) => {
    console.log('Success:', values)
    window.fetch('LoginServlet', {
      method: 'POST',
      headers: {
        'Context-type': 'application/json'
      },
      body: JSON.stringify(values)
    }).then(res => res.json())
      .then((result) => {
        this.setState({
          userId: result.userId,
          username: result.username,
          isLoaded: true
        })
      },
      (error) => {
        this.setState({
          isLoaded: true,
          error
        })
      }
      )
    LoginServlet(values)
  }

  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo)
  }

  return (
    <Layout>
      <Header>
        <div className='logo' style={{ float: 'left' }}><img src='favicon.ico' alt='logo' /></div>
        <Menu theme='dark' mode='horizontal' defaultSelectedKeys='1' style={{ float: 'left' }}>
          <Menu.Item key='1'>首页</Menu.Item>
          <Menu.Item key='2'>我的文件</Menu.Item>
        </Menu>
      </Header>
      <Form
        name='basic'
        labelCol={{
          span: 8
        }}
        wrapperCol={{
          span: 8
        }}
        initialValues={{
          remember: true
        }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete='off'
        style={{ padding: '24px' }}
      >
        <Form.Item
          label='用户名'
          name='username'
          rules={[
            {
              required: true,
              message: '请输入用户名！'
            }
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label='密码'
          name='password'
          rules={[
            {
              required: true,
              message: '请输入密码！'
            }
          ]}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item
          name='remember'
          valuePropName='checked'
          wrapperCol={{
            offset: 8,
            span: 8
          }}
        >
          <Checkbox>记住我</Checkbox>
        </Form.Item>

        <Form.Item
          wrapperCol={{
            offset: 8,
            span: 8
          }}
        >
          <Button type='primary' htmlType='submit'>
            登录
          </Button>
        </Form.Item>
      </Form>
    </Layout>
  )
}

ReactDOM.render(<Login />, document.getElementById('root'))
export default Login

class LoginServlet extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      userId: '',
      username: '',
      error: null,
      isLoaded: false
    }
  }

  componentDidMount () {
    window.fetch('LoginServlet', {
      method: 'POST',
      headers: {
        'Context-type': 'application/json'
      },
      body: JSON.stringify(this.props)
    }).then(res => res.json())
      .then((result) => {
        this.setState({
          userId: result.userId,
          username: result.username,
          isLoaded: true
        })
      },
      (error) => {
        this.setState({
          isLoaded: true,
          error
        })
      }
      )
  }

  render () {
    const { error, isLoaded, userId, username } = this.state
    if (error) {
      return <div>Error: {error.message}</div>
    } else if (!isLoaded) {
      return <div>Loading...</div>
    } else {
      return (
        <div>
          <div>{userId}</div>
          <div>{username}</div>
        </div>
      )
    }
  }
}
