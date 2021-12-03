'use strict'

class Login extends React.Component {
    constructor() {
        super()
        const username = localStorage.username
        this.state = { username: username }
    }

    handleLoginClick() {
        const username = $('#username').val()
        const password = $('#password').val()
        const regex1 = /^[a-zA-Z]\w{4,15}$/
        const regex2 = /^[a-zA-Z]\w{5,17}$/
        if (!regex1.test(username)) {
            if (username == null || username == '') {
                window.alert('请输入账号！')
            } else {
                window.alert('账号不合法！')
            }
        } else if (!regex2.test(password)) {
            if (password == null || password == '') {
                window.alert('请输入密码！')
            } else {
                window.alert('密码不合法')
            }
        } else {
            $.post('UserServlet', { username, password, flag: 'login' }, (result) => {
                if (result != 0) {
                    sessionStorage.userId = result
                    $(location).attr('href', 'index.html')
                } else {
                    window.alert('账号或密码错误')
                }
            })
        }
    }

    handleLogupClick() {
        $(location).attr('href', 'logup.html')
    }

    handleBackClick() {
        $(location).attr('href', 'index.html')
    }

    render() {
        return (
            <div id='login'>
                <h1>登录</h1>
                <table>
                    <tbody>
                        <tr>
                            <td>账号</td>
                            <td>
                                <input type='text' id='username' placeholder='请输入账号' defaultValue={this.state.username} />
                            </td>
                        </tr>
                        <tr>
                            <td>密码</td>
                            <td>
                                <input type='password' id='password' placeholder='请输入密码' />
                            </td>
                        </tr>
                        <tr>
                            <td />
                            <td>
                                <Remember />
                            </td>
                        </tr>
                        <tr>
                            <td />
                            <td>
                                <button onClick={this.handleLoginClick}>登录</button>
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

class Remember extends React.Component {
    constructor() {
        super()
        if (localStorage.username == '' || localStorage.username == null) {
            this.state = { checked: false }
        } else {
            this.state = { checked: true }
        }
        this.handleRememberClick = this.handleRememberClick.bind(this)
    }

    handleRememberClick() {
        if (this.state.checked) {
            this.setState({ checked: false })
            localStorage.username = ''
            localStorage.clear()
        } else {
            this.setState({ checked: true })
            localStorage.username = $('#username').val()
        }
    }

    componentWillUnmount() {
        if (this.state.checked) {
            localStorage.username = $('#username').val()
        }
    }

    render() {
        return (
            <>
                {
                    this.state.checked
                        ? <input type='checkbox' id='remember' onClick={this.handleRememberClick} defaultChecked='checked' />
                        : <input type='checkbox' id='remember' onClick={this.handleRememberClick} />
                }
                记住我
            </>
        )
    }
}

ReactDOM.render(<Login />, document.getElementById('root'))
