'use strict'

let userId = sessionStorage.userId

class App extends React.Component {
    constructor() {
        super()
        if (userId == null) {
            $(location).attr('href', 'login.html')
        }
        this.state = { authority: '私密' }
        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleChange = this.handleChange.bind(this)
    }

    handleSubmit() {
        $.post('FileServlet', { flag: 'upload1', userId: userId, authority: this.state.authority })
    }

    handleChange(event) {
        this.setState({ authority: event.target.value })
    }

    render() {
        return (
            <>
                <Header />
                <main>
                    <label>
                        权限：
                        <select value={this.state.authority} onChange={this.handleChange}>
                            <option value='公开'>公开</option>
                            <option value='私密'>私密</option>
                        </select>
                    </label>
                    <form method='post' action='FileServlet' encType='multipart/form-data'>
                        <label>
                            上传文件：
                            <input type="file" id='file' name='file' multiple required />
                        </label>
                        <br />
                        <button type='submit' onClick={this.handleSubmit}>上传</button>
                    </form>
                    <table>
                        <Filethead />
                        <Filetbody />
                    </table>
                </main>
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
                <a href='index.html'><button>首页</button></a>
                <a href='myfile.html'><button>我的文件</button></a>
                <button onClick={this.handleLogoutClick}>退出</button>
            </header>
        )
    }
}

class Filethead extends React.Component {
    render() {
        return (
            <thead>
                <tr>
                    <th>文件名</th>
                    <th>权限</th>
                    <th>下载</th>
                    <th>删除</th>
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

    handleDeleteClick(id, e) {
        $.post('FileServlet', { fileId: id, flag: 'delete' }, () => {
            $(location).attr('href','myfile.html')
        })
    }

    componentDidMount() {
        $.post(
            'FileServlet',
            {
                userId,
                page: this.state == null ? 1 : this.state.currentPage,
                flag: 'lookme'
            },
            (data) => {
                const json = JSON.parse(data)
                const files = json.files
                const fileItems = files.map((file) =>
                    <tr key={file.fileId}>
                        <td>{file.fileName}</td>
                        <td>{file.authority}</td>
                        <td>
                            <a target='_blank' href={'FileServlet?fileId=' + file.fileId}><button>下载</button></a>
                        </td>
                        <td><button onClick={(e) => this.handleDeleteClick(file.fileId, e)}>删除</button></td>
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

ReactDOM.render(<App />, document.getElementById('root'))