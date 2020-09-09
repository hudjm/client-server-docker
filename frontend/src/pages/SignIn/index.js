import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";

import axios from 'axios'
import { login, getToken } from "../../services/auth";

import { Form, Container } from "./styles";

class SignIn extends Component {
  state = {
    usuario: "",
    senha: "",
    error: ""
  };

  handleSignIn = async e => {
    e.preventDefault();
    const { usuario, senha } = this.state;
    if (!usuario || !senha) {
      this.setState({ error: "Preencha usuário e senha para continuar!" });
    } else {
      axios.post('http://localhost:8080/auth', { usuario, senha },
                                        { header: {'Content-Type': 'application/json',
                                         'Accept': 'application/json'}})
            .then(resp => {
              axios.interceptors.request.use(config => {

                console.log('interceptor')
                const token = getToken();
                if (token) {
                  config.headers.Authorization = `Basic ${token}`;
                }
                return config;
              }, error => {
                console.log('erro intercpet')
                console.log(error)
                console.log(error.response.status)
                if (error.response.status === 401) {
                  this.props.history.push("/");
                }

                return Promise.reject(error);
              });
              

              login(resp.data.dados.token);
              this.props.history.push("/app");
            })
            .catch(error => {
              var msg = error.response.data.error

              if (error.response.data.errors){
                msg = '';
                error.response.data.errors.map(e => (
                  msg = msg + ' ' + e
                ))
              } 

              this.setState({
                error:
                  "Houve um problema com o login, verifique suas credenciais. T.T => " + msg
              });

            })


    }
  };

  render() {
    return (
      <Container>
        <Form onSubmit={this.handleSignIn}>
         {this.state.error && <p>{this.state.error}</p>}
          <input
            type="usuario"
            placeholder="Usuário"
            onChange={e => this.setState({ usuario: e.target.value })}
          />
          <input
            type="password"
            placeholder="Senha"
            onChange={e => this.setState({ senha: e.target.value })}
          />
          <button type="submit">Entrar</button>
          <hr />
          <Link to="/signup">Criar conta grátis</Link>
        </Form>
      </Container>
    );
  }
}

export default withRouter(SignIn);
