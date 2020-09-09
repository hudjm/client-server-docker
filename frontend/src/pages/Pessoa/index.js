import React, { Component } from "react";


import {
 withRouter
} from "react-router-dom";

import axios from 'axios'

import { Form, Container } from "./styles";

class Pessoa extends Component {

  constructor(props) {
    super(props);

    this.state = {
      id: 0,
      nome: "",
      cpf: "",
      sexo: "",
      naturalidade: "",
      nacionalidade: "",
      dataNascimento: undefined,
      error: ""
    };

    this.mostrarErro = this.mostrarErro.bind(this);
    this.carregarPessoa = this.carregarPessoa.bind(this);
    
  }

  componentDidMount(){    
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
      var pair = vars[i].split("=");
     
      if(pair[0] === 'id'){
        this.carregarPessoa(pair[1])
      }
    }
  }

  carregarPessoa(id) {
    axios.get('http://localhost:8080/api/pessoa/'+id, 
          { header: {'Content-Type': 'application/json',
                     'Accept': 'application/json'}})
      .then(resp => {
        console.log(resp)
        this.setState({
          id: resp.data.dados.id,
          nome: resp.data.dados.nome,
          cpf: resp.data.dados.cpf,
          sexo: resp.data.dados.sexo,
          naturalidade: resp.data.dados.naturalidade,
          nacionalidade: resp.data.dados.nacionalidade,
          dataNascimento: resp.data.dados.dataNascimento,
          error: ""          
        });
      })
      .catch(error => {
          if (error.response.data.errors){
            var msg = '';
            error.response.data.errors.map(e => (
              msg = msg + ' ' + e
            ))
         
            this.mostrarErro(msg)
          } 
          else {
            this.mostrarErro(error.response.data.error)
          }

      })
  }


  mostrarErro(erro) {
    this.setState({
      error : erro
    });
  }

  salvar = async e => {
    e.preventDefault();

    const { id, cpf, nome, sexo, naturalidade, nacionalidade, dataNascimento } = this.state;
    if (!cpf || !nome || !sexo) {
      this.setState({ error: "Preencha Nome|CPF|Sexo  para se cadastrar" });
    } else {
      var method = id === 0 ? 'post' : 'put'
      const ids = id && id !== 0 ? id : ''

      axios[method]('http://localhost:8080/api/pessoa/'+ids, 
                      { id, cpf, nome, sexo, naturalidade, nacionalidade, dataNascimento },
                                        { header: {'Content-Type': 'application/json',
                                         'Accept': 'application/json'}})
            .then(resp => {
              this.props.history.push("/app");
            })
            .catch(error => {
              if (error.response.data.errors){
                var msg = '';
                error.response.data.errors.map(e => (
                  msg = msg + ' ' + e
                ))
                this.mostrarErro(msg)
              } 
              else {
                this.mostrarErro(error.response.data.error)
              }
              
            })
    }
  };

  render() {
    return (
      <Container>
        <Form onSubmit={this.salvar}>
         
         {this.state.error && <p>{this.state.error}</p>}

                 
         <input
            type={this.state.id === 0 ? 'hidden' : "number"}
            placeholder="Código Pessoa"
            readOnly="true"         
            value={this.state.id}
            onChange={e => this.setState({ id: e.target.value })}
          />
          <input
            type="text"
            placeholder="Nome"
            value={this.state.nome}
            onChange={e => this.setState({ nome: e.target.value })}
          />
          <input
            type="text"
            placeholder="CPF"
            value={this.state.cpf}
            onChange={e => this.setState({ cpf: e.target.value })}
          />
          <input
            type="text"
            placeholder="Sexo"
            value={this.state.sexo}
            onChange={e => this.setState({ sexo: e.target.value })}
          />
           <input
            type="date"
            placeholder="Data nascimento"
            value={this.state.dataNascimento}
            onChange={e => this.setState({ dataNascimento: e.target.value })}
          />
          <input
            type="text"
            placeholder="Naturalidade"
            value={this.state.naturalidade}
            onChange={e => this.setState({ naturalidade: e.target.value })}
          />
          <input
            type="text"
            placeholder="Nacionalidade"
            value={this.state.nacionalidade}
            onChange={e => this.setState({ nacionalidade: e.target.value })}
          />
          
          <button type="submit">Salvar</button>
        </Form>
      </Container>
    );
  }
}

export default withRouter(Pessoa);
