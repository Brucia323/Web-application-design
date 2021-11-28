'use strict'

let userId = sessionStorage.userId
let username = sessionStorage.username

class App extends React.Component {
    constructor() {
        super()
        if (userId == null) {
            this.state = { login: false }
        } else {
            this.state = { login: true }
        }
    }

    render() {
        return (
            <>
                <Header login={this.state.login} />
                <main>
                    <table>
                        <Filethead login={this.state.login} />
                        <Filetbody login={this.state.login} />
                    </table>
                </main>
            </>
        )
    }
}


class Filethead extends React.Component {
    render() {
        return (
            <thead>
                <tr>
                    <th>文件名</th>
                    <th>用户名</th>
                    {this.props.login && <th>操作</th>}
                </tr>
            </thead>
        )
    }
}

class Filetbody extends React.Component {
    constructor() {
        super()
        this.handleLastPageClick = this.handleLastPageClick.bind(this)
        this.handleNextPageClick = this.handleNextPageClick.bind(this)
    }

    handleLastPageClick() {
        this.setState({
            currentPage: this.state.currentPage == 1
                ? 1
                : this.state.currentPage - 1
        })
    }

    handleNextPageClick() {
        this.setState({
            currentPage: this.state.currentPage == this.state.totalPage
                ? this.state.totalPage
                : this.state.currentPage + 1
        })
    }

    componentDidMount() {
        $.post(
            'FileServlet',
            {
                login: this.props.login,
                page: this.state == null ? 1 : this.state.currentPage,
                flag: 'look'
            },
            (data) => {
                const json = JSON.parse(data)
                const files = json.files
                const fileItems = files.map((file) =>
                    <tr key={file.fileId}>
                        <td>{file.fileName}</td>
                        <td>{file.username}</td>
                        {this.props.login && (
                            <td>
                                <button>下载</button>
                            </td>
                        )}
                    </tr>
                )
                const page = (
                    <tr>
                        <td><button onClick={this.handleLastPageClick}>上一页</button></td>
                        <td>{json.currentPage}/{json.totalPage}</td>
                        <td><button onClick={this.handleNextPageClick}>下一页</button></td>
                    </tr>)
                this.setState({
                    fileItems: fileItems,
                    page: page,
                    currentPage: json.currentPage,
                    totalPage: json.totalPage
                })
            }
        )
    }

    render() {
        return (
            <>
                {this.state != null && <tbody>{this.state.fileItems}{this.state.page}</tbody>}
            </>
        )
    }
}

class Header extends React.Component {
    handleLogoutClick() {
        sessionStorage.clear()
        $(location).attr('href', 'login.html')
    }

    render() {
        return (
            <header id='header'>
                <button>首页</button>
                {this.props.login && <a href='myfile.html'><button>我的文件</button></a>}
                {this.props.login && <a href='me.html'><button>个人中心</button></a>}
                {this.props.login
                    ? <button onClick={this.handleLogoutClick}>退出</button>
                    : <a href='login.html'><button>登录/注册</button></a>}
            </header>
        )
    }
}

ReactDOM.render(<App />, document.getElementById('root'))
