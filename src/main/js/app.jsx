import React, {Component} from "react";
import ReactDOM from "react-dom";
import SectorList from "./container/SectorList.jsx";
import NameInput from "./container/NameInput.jsx";
import InputError from "./container/InputError.jsx";

class App extends Component {

  constructor() {
    super();
    this.state = {name: "", userSectors: [], tc: false, valid: false, errorVisible: false, errorMessage: "Please fill all fields"};

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

  componentDidMount() {
    fetch(`http://localhost:8080/getCustomerForm`)
      .then(result => result.json())
      .then(customerForm =>
        this.setState({
          name: customerForm.name,
          userSectors: customerForm.sectors,
          tc: customerForm.tc
        })
      );
  }

  postCustomerData(customerDataForm) {
    fetch('http://localhost:8080/saveCustomerForm', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(customerDataForm)
    }).then(() => {alert("Data successfully saved")});
  }


  render() {

    return (
      <div>
        <p>Please enter your name and pick the Sectors you are currently involved in.</p>

        <NameInput value={this.state.name} onChange={this.handleNameChange}/>

        <SectorList value={this.state.userSectors} onChange={this.handleSectorsChange}/>

        <div className="form-elem">
          <input type="checkbox" value={this.state.tc} onClick={() => this.setState({tc: !this.state.tc})}/>
          <label>Agree to terms</label>
        </div>

        <InputError visible={this.state.errorVisible && !this.state.valid} errorMessage={this.state.errorMessage}/>

        <button onClick={() => {
          let customerDataForm = {tc: this.state.tc, name: this.state.name, sectors: this.state.userSectors};

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