'use strict'

class App extends React.Component {
    constructor() {
        super()
        let wareList = sessionStorage.getItem('wareList')
        wareList = wareList ? JSON.parse(wareList) : []
        this.state = {
            wareList: wareList,
        }
        this.handleClick = this.handleClick.bind(this)
    }

    reduce(index) {
        let wareList = this.state.wareList
        if (wareList[index].wareNum > 1) {
            wareList[index].wareNum--
            this.setState({
                wareList: wareList
            })
            sessionStorage.setItem('wareList', JSON.stringify(wareList))
        } else if (wareList[index].wareNum == 1) {
            wareList.splice(index, 1)
            this.setState({
                wareList: wareList
            })
            sessionStorage.setItem('wareList', JSON.stringify(wareList))
        }
    }

    add(index) {
        let wareList = this.state.wareList
        wareList[index].wareNum++
        this.setState({
            wareList: wareList
        })
        sessionStorage.setItem('wareList', JSON.stringify(wareList))
    }

    handleClick() {
        let wareList = this.state.wareList
        let wareIdList = []
        let wareNumList = []
        wareList.forEach(item => {
            wareIdList.push(item.wareId)
            wareNumList.push(item.wareNum)
        })
        wareIdList = JSON.stringify(wareIdList)
        wareNumList = JSON.stringify(wareNumList)
        $.post('CreateServlet', { wareIdList, wareNumList }, (res) => {
            if (res == 'success') {
                alert('提交订单成功')
                sessionStorage.removeItem('wareList')
                window.location.href = 'index.html'
            } else {
                alert('提交订单失败')
            }
        })
    }

    render() {
        return (
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>商品名称</th>
                            <th>商品数量</th>
                            <th>商品单价</th>
                            <th>减少</th>
                            <th>增加</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.wareList.map((item, index) => {
                                return (
                                    <tr key={index}>
                                        <td>{item.wareName}</td>
                                        <td>{item.wareNum}</td>
                                        <td>{item.warePrice}</td>
                                        <td><button onClick={() => this.reduce(index)}>-</button></td>
                                        <td><button onClick={() => this.add(index)}>+</button></td>
                                    </tr>
                                )
                            })
                        }
                    </tbody>
                </table>
                <button onClick={this.handleClick}>提交订单</button>
            </div>
        )
    }
}

ReactDOM.render(<App />, document.getElementById('root'))