import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { User } from '../../models/user';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  // Arrange Data

   // Error message
  errorMessage: string = '';
    
  // Constructor
  constructor(private userAuth: Auth) {}

  // Create the obejct and features
  user: User = {
    // Initialize fieds
    username: '',
    password: ''
  };

  //Reset form
  private resetForm(): void {
    this.user = {
      username: '',
      password: ''
    };
  }
  // Custom Messages
  private userCreatedMessage(): void {
      console.log("User created!")
    }

  private userLoggedInMessage(): void {
      console.log("User loged in!")
    }



  // register method: onRegister()
  // On Register we want to set the user's fields and send to the backend
  public onRegister() : void{
    this.userAuth.register(this.user).subscribe({
      next: () =>{
        this.resetForm();
        this.userCreatedMessage();
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Something went wrong.';
        console.error('Error creating user:', error);
      }
    });
  }

  // login method: onLogin()
  // on login , we want to get the users fields and send to the backend

  public onLogin() : void {
    this.userAuth.login(this.user).subscribe({
      next: ( loggedInUser ) =>{

        //save token 
        this.userAuth.saveToken(loggedInUser.token);

        // log it
        console.log('token: ', loggedInUser.token );
        this.userLoggedInMessage();
        this.resetForm();
  },
    error: (error) => {
        this.errorMessage = error.error?.message || 'Something went wrong.';
        console.error('Error logging in:', error);
      }

  });
}

logout(): void {
  localStorage.removeItem('token');
}

}