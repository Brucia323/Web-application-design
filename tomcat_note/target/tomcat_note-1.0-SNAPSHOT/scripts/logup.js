'use strict'

class Logup extends React.Component {
    constructor() {
        super()
        this.state = { username: '', password: '' }

        this.handleChangeUsername = this.handleChangeUsername.bind(this)
        this.handleChangePassword = this.handleChangePassword.bind(this)
        this.handleBlurUsername = this.handleBlurUsername.bind(this)
        this.handleBlurPassword = this.handleBlurPassword.bind(this)
        this.handleLogupClick = this.handleLogupClick.bind(this)
    }

    handleChangeUsername(event) {
        this.setState({ username: event.target.value })
    }

    handleChangePassword(event) {
        this.setState({ password: event.target.value })
    }

    handleBlurUsername() {
        const username = this.state.username
        const regex = /^[a-zA-Z]\w{4,15}$/
        if (regex.test(username)) {
            $.post('UserServlet',
                {
                    username,
                    flag: 'checkUsername'
                },
                (result) => {
                    if (result == 0) {
                        window.alert('账号已被注册')
                        this.setState({ username: '' })
                    }
                })
        } else {
            if (username != '') {
                window.alert('账号不合法')
                this.setState({ username: '' })
            }
        }
    }

    handleBlurPassword() {
        const password = this.state.password
        const regex1 = /^[a-zA-Z]\w{5,17}$/
        if (regex1.test(password)) {
            return true
        } else {
            if (password != '') {
                this.setState({ password: '' })
                window.alert('密码不合法')
            }
            return false
        }
    }

    handleLogupClick() {
        const username = this.state.username
        const password = this.state.password
        if (username != '' && password != '') {
            $.post(
                'UserServlet',
                {
                    username,
                    password,
                    flag: 'logup'
                },
                (result) => {
                    if (result !== 0) {
                        sessionStorage.userId = result
                        $(location).attr('href', 'index.html')
                    } else {
                        window.alert('注册失败')
                    }
                }
            )
        } else {
            if (username == '') {
                window.alert('请输入账号')
            } else if (password == '') {
                window.alert('请输入密码')
            }
        }
    }

    handleBackClick() {
        $(location).attr('href', 'login.html')
    }

    render() {
        return (
            <div id='logup'>
                <h1>注册</h1>
                <table>
                    <tbody>
                        <tr>
                            <td>账号</td>
                            <td>
                                <input
                                    type='text'
                                    id='username'
                                    placeholder='请输入账号'
                                    onBlur={this.handleBlurUsername}
                                    value={this.state.username}
                                    onChange={this.handleChangeUsername}
                                />
                            </td>
                        </tr>
                        <tr>
                            <td>密码</td>
                            <td>
                                <input
                                    type='password'
                                    id='password'
                                    placeholder='请输入密码'
                                    onBlur={this.handleBlurPassword}
                                    value={this.state.password}
                                    onChange={this.handleChangePassword}
                                />
                            </td>
                        </tr>
                        <tr>
                            <td />
                            <td>
                                <button onClick={this.handleLogupClick}>注册</button>
                                <button onClick={this.handleBackClick}>返回</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        )
    }
}

ReactDOM.render(<Logup />, document.getElementById('root'))
