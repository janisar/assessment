import React, {Component} from "react";
import ReactDOM from "react-dom";
import SectorList from "./container/SectorList.jsx";
import NameInput from "./container/NameInput.jsx";
import TextInput from "./container/TextInput.jsx";

class App extends Component {

  constructor() {
    super();
    this.state = {
      name: "",
      userSectors: [],
      tc: false,
      valid: false,
      errorVisible: false,
      errorMessage: "Please fill all fields",
      saved: false,
      successMessage: "Your details are successfully saved"
    };

    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleSectorsChange = this.handleSectorsChange.bind(this);
  }

  handleNameChange(event) {
    this.setState({name: event.target.value});
  }

  handleSectorsChange(value) {
    this.setState({userSectors: value});
  }


  isValid(customerDataForm) {
    if (customerDataForm.sectors.length === 0 || customerDataForm.name.trim().length <= 0 || !customerDataForm.tc) {
      this.setState({valid: false, errorVisible: true});
      return false;
    }

    return true;
  }

  postCustomerData(customerDataForm) {
    fetch('http://localhost:8080/saveCustomerForm', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(customerDataForm)
    }).then((response) => response.json()).then(customerForm => {
      localStorage.clear();
      localStorage.setItem('customerForm', JSON.stringify(customerForm));

      this.setState({saved: true});
    });
  }


  componentDidMount() {
    let form = localStorage.getItem('customerForm');

    if (form !== undefined) {
      let customerForm = JSON.parse(form);
      console.log(customerForm);
      this.setState({
        id: customerForm.id,
        name: customerForm.name,
        userSectors: customerForm.sectors,
        tc: customerForm.tc
      });
    }
  }

  render() {
    return (
      <div>
        <p>Please enter your name and pick the Sectors you are currently involved in.</p>

        <NameInput value={this.state.name} onChange={this.handleNameChange}/>

        <SectorList sectors={this.state.userSectors} onChange={this.handleSectorsChange}/>

        <div className="form-elem">
          <input type="checkbox" checked={this.state.tc} onClick={() => this.setState({tc: !this.state.tc})}/>
          <label>Agree to terms</label>
        </div>

        <TextInput visible={this.state.saved} message={this.state.successMessage}/>
        <TextInput visible={this.state.errorVisible && !this.state.valid} message={this.state.errorMessage}/>

        <button onClick={() => {
          let customerDataForm = {id: this.state.id, tc: this.state.tc, name: this.state.name, sectors: this.state.userSectors};

          if (this.isValid(customerDataForm)) {
            this.postCustomerData(customerDataForm);
          }
        }}>Save
        </button>
      </div>
    )
  }
}

ReactDOM.render(<App />, document.getElementById('app'));