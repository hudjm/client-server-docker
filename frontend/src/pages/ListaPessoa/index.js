import React, { Component } from "react";

import {
  withRouter,
} from "react-router-dom";

import axios from 'axios'

import { Form, Container } from "./styles";

class ListaPessoa extends Component {

  constructor(props) {
    super(props);
    this.state = {
      lista: [],
      error: ""
    }


    this.mostrarErro = this.mostrarErro.bind(this);
  }

  componentDidMount() {
    axios.get('http://localhost:8080/api/pessoa', 
          { header: {'Content-Type': 'application/json',
                     'Accept': 'application/json'}})
      .then(resp => {
        console.log(resp)
        this.setState({
          lista : resp.data.dados
        });
      })
      .catch(error => {
          var msg = this.mostrarErro(error.response.data.error)

          if (error.response.data.errors){
            msg = '';
            error.response.data.errors.map(e => (
              msg = msg + ' ' + e
            ))
          } 

          this.mostrarErro(msg)

      })
  }


  mostrarErro(erro) {
    this.setState({
      error : erro
    });
  }

  cadastrar(){
    
    this.props.history.push("/pessoa");
  }

  atualizar(pessoa){
    this.props.history.push("/pessoa?id="+pessoa.id);
  }

  delete(pessoa){
    console.log('deletar')
  }

  renderRows() {
    const list = this.state.lista || []
    var i = 0

    return  list.map(bc => (
        <tr key={bc.id || i++}>
            <td>{bc.nome}</td>
            
            <td>
                <button className="button2" type="button" onClick={() => this.atualizar(bc)}>
                    <i className='fa fa-pencil'></i>
                </button>
                <button type="button" onClick={() => this.delete(bc)}>
                    <i className='fa fa-trash-o'></i>
                </button>
            </td>
        </tr>
    ))
  }

  render() {        
    return (
      <Container>
        <Form>
          <div>
            <button className="button1" type="button" onClick={() => this.cadastrar()}>Cadastrar</button>
            {this.state.error && <p>{this.state.error}</p>}            
            <div>
                <table className='table'>
                    <thead>
                        <tr>
                            <th></th>
                            <th className='table-actions'></th>
                        </tr>
                    </thead>
                    <tbody>                                        
                        {this.renderRows()}
                    </tbody>                    
                </table>
                
            </div>
          </div>
        </Form>
      </Container>
    )
}

}

export default withRouter(ListaPessoa);
