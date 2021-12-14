'use strict'

var wareList = []

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            trs: []
        }
    }

    handleClick(e, wareId) {
        console.log('click')
        let wareName = e.target.parentNode.parentNode.firstChild.innerHTML
        let warePrice = e.target.parentNode.parentNode.firstChild.nextSibling.innerHTML
        if (wareList.length == 0) {
            wareList.push({
                wareId: wareId,
                wareName: wareName,
                warePrice: warePrice,
                wareNum: 1
            })
        } else {
            let flag = true
            for (let ware in wareList) {
                if (ware.wareId == wareId) {
                    ware.wareNum++
                    flag = false
                }
            }
            if (flag) {
                wareList.push({
                    wareId: wareId,
                    wareName: wareName,
                    warePrice: warePrice,
                    wareNum: 1
                })
            }
        }
    }

    componentDidMount() {
        $.post('WareServlet', (data) => {
            console.log(data)
            data = JSON.parse(data)
            let trs = data.map((item) => {
                return (
                    <tr key={item.wareId}>
                        <td>{item.wareName}</td>
                        <td>{item.warePrice}</td>
                        <td>{item.wareType}</td>
                        <td>
                            <button onClick={(e) => { this.handleClick(e, item.wareId) }}>添加购物车</button>
                        </td>
                    </tr>
                )
            })
            this.setState({
                trs: trs
            })
        })
    }

    handleCartClick() {
        console.log(wareList)
        sessionStorage.setItem('wareList', JSON.stringify(wareList))
        window.location.href = 'cart.html'
    }

    render() {
        return (
            <div>
                <button onClick={this.handleCartClick}>购物车</button>
                <table>
                    <thead>
                        <tr>
                            <th>商品名称</th>
                            <th>商品价格</th>
                            <th>商品类型</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.trs}
                    </tbody>
                </table>
            </div>
        )
    }
}

ReactDOM.render(<App />, document.getElementById('root'))
