import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";

import axios from 'axios'

import { Form, Container } from "./styles";

class SignUp extends Component {
  state = {
    username: "",
    nome: "",
    password: "",
    error: ""
  };


  handleSignUp = async e => {
    e.preventDefault();
    const { username, nome, password } = this.state;
    if (!username || !nome || !password) {
      this.setState({ error: "Preencha todos os dados para se cadastrar" });
    } else {
      axios.post('http://localhost:8080/signup', { username, nome, password },
                                        { header: {'Content-Type': 'application/json',
                                         'Accept': 'application/json'}})
            .then(resp => {
              this.props.history.push("/");
            })
            .catch(error => {
              console.log(error)
              console.log(error.response)

              var msg = error.response.data.error

              if (error.response.data.errors){
                msg = '';
                error.response.data.errors.map(e => (
                  msg = msg + ' ' + e
                ))
              } 

              this.setState({
                error:
                  "Ocorreu um erro ao registrar sua conta. T.T => " + msg
              });

            })
    }
  };

  render() {
    return (
      <Container>
        <Form onSubmit={this.handleSignUp}>
         {this.state.error && <p>{this.state.error}</p>}
          <input
            type="text"
            placeholder="Nome de usuário"
            onChange={e => this.setState({ username: e.target.value })}
          />
          <input
            type="text"
            placeholder="Nome"
            onChange={e => this.setState({ nome: e.target.value })}
          />
          <input
            type="password"
            placeholder="Senha"
            onChange={e => this.setState({ password: e.target.value })}
          />
          <button type="submit">Cadastrar grátis</button>
          <hr />
          <Link to="/">Fazer login</Link>
        </Form>
      </Container>
    );
  }
}

export default withRouter(SignUp);
